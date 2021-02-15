package com.setge.talkingtoday.security;

import com.setge.talkingtoday.entity.Member;
import com.setge.talkingtoday.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
@Service
public class MemberUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("UserDetailsService loadUserByUsername : " + username);

        Optional<Member> result = memberRepo.findByEmail(username, false);

        if (result.isEmpty()) { // email이 존재하지 않는다면..
            throw new UsernameNotFoundException("check Email or Social");
        }

        Member member = result.get();
        log.info("member : " + member);

        // member -> authMemberDTO
        AuthMemberDTO authMemberDTO = new AuthMemberDTO(
                member.getMid(),
                member.getEmail(),
                member.getPassword(),
                member.getNickname(),
                member.isFromSocial(),
                member.getRoleSet().stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name())).collect(Collectors.toSet())
        );

        return authMemberDTO;
    }


}
