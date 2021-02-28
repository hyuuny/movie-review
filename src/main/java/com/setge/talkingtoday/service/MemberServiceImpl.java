package com.setge.talkingtoday.service;

import com.setge.talkingtoday.dto.MemberDTO;
import com.setge.talkingtoday.entity.Member;
import com.setge.talkingtoday.entity.Role;
import com.setge.talkingtoday.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Log4j2
@Transactional(readOnly = true) // register 외에는 전부 읽기만..
@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepo;
    private final PasswordEncoder passwordEncoder;


    /**
     * 회원 가입
     */
    @Transactional
    @Override
    public Long join(MemberDTO dto) {

        validateDuplicateMember(dto); // 중복 회원 검사

        dto.setPassword(passwordEncoder.encode(dto.getPassword())); // 비밀번호 암호화
        Member member = dtoToEntity(dto);
        member.addMemberRole(Role.USER); // 권한은 유저

        memberRepo.save(member);
        return member.getMid();
    }

    private void validateDuplicateMember(MemberDTO dto) {
        memberRepo.findByEmail(dto.getEmail())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 이메일입니다.");
                });

        memberRepo.findByNickname(dto.getNickname())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 닉네임입니다");
                });
    }

    /**
     * 회원가입 email 중복체크
     */
    @Override
    public int duplicateEmailCheck(String email) {
        Optional<Member> result = memberRepo.findByEmail(email);
        return result.isPresent() ? 1 : 0;
    }

    /**
     * 회원가입 nickname 중복체크
     */
    @Override
    public int duplicateNicknameCheck(String nickname) {
        Optional<Member> result = memberRepo.findByNickname(nickname);
        return result.isPresent() ? 1 : 0;
    }

    /**
     * 닉네임 변경
     */
    @Transactional
    @Override
    public void changeNickname(String nickname, Long mid) {
        Member member = memberRepo.findById(mid).get();
        String oldNickname = member.getNickname();

        memberRepo.changeNickname(nickname, mid);
        memberRepo.modifyMovieWriterByNickname(nickname, oldNickname); // 리뷰게시판 작성자 수정
        memberRepo.modifyReplyer(nickname, oldNickname); // 자유게시판, 리뷰게시판 댓글 닉네임 변곁
    }

    /**
     * 비밀번호 변경
     */
    @Override
    public void changePassword(String newPwd, Long mid) {
        memberRepo.changePassword(passwordEncoder.encode(newPwd), mid);
    }

    /**
     * 비밀번호 일치 확인
     */
    @Override
    public boolean isPwdMatchesCheck(String password, Long mid) {

        String encodePwd = memberRepo.findById(mid).get()
                            .getPassword();

        return passwordEncoder.matches(password, encodePwd);
    }


}
