package com.setge.talkingtoday.security;

import com.setge.talkingtoday.entity.Member;
import com.setge.talkingtoday.entity.Role;
import com.setge.talkingtoday.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
@Service
public class OAuth2UserDetailsService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        log.info("OAuth2UserRequest : " + userRequest); // org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest 객체

        String clientName = userRequest.getClientRegistration().getClientName();
        log.info("clientName : " + clientName);
        log.info(userRequest.getAdditionalParameters());

        OAuth2User oAuth2User = super.loadUser(userRequest);

        // oAuth2User 정보 확인
        oAuth2User.getAttributes().forEach((k, v) -> {
            log.info(k + " : " + v); // sub, picture, email, email_verified, hd
        });

        String email = "";

        if (clientName.equals("Google")) { // 구글 로그인인 경우
            email = oAuth2User.getAttribute("email");
        }
        log.info("EMAIL : " + email);

        Member member = saveSocialMember(email); // 소셜 회원 정보 저장(없으면 자동 가입되어 저장)
        AuthMemberDTO authMemberDTO = new AuthMemberDTO(
                member.getMid(),
                member.getEmail(),
                member.getPassword(),
                member.getNickname(),
                true, // fromSocial 소셜로그인을 해야만 true, 폼 로그인의 경우 false
                member.getRoleSet().stream().map(
                        role -> new SimpleGrantedAuthority("ROLE_" + role.name())
                ).collect(Collectors.toList()), oAuth2User.getAttributes()
        );

        return authMemberDTO;
    }

    // 소셜로그인 처리(회원이 아니면 회원가입 처리)
    private Member saveSocialMember(String email) {

        // 기존 동일한 이메일로 가입한 회원이 있으면 그대로 조회만..
        Optional<Member> socialMember = memberRepo.findByEmail(email, true);

        if (socialMember.isPresent()) { // null 확인
            return socialMember.get();
        }

        // 없다면 회원 추가
        Member member = Member.builder()
                .email(email)
                .nickname(email)
                .password(passwordEncoder.encode("1111"))
                .fromSocial(true)
                .build();
        member.addMemberRole(Role.USER);
        memberRepo.save(member);

        return member;
    }
}
