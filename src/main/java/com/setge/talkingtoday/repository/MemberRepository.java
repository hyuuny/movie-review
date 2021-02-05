package com.setge.talkingtoday.repository;

import com.setge.talkingtoday.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, String> {
}
