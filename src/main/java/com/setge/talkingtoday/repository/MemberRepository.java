package com.setge.talkingtoday.repository;

import com.setge.talkingtoday.entity.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @EntityGraph(attributePaths = {"roleSet"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("select m from Member m where m.fromSocial = :social and m.email =:email")
    Optional<Member> findByEmail(String email, boolean social);

    @EntityGraph(attributePaths = {"roleSet"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("select m from Member m where m.email =:email")
    Optional<Member> findByEmail(String email);

    @EntityGraph(attributePaths = {"roleSet"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("select m from Member m where m.nickname = :nickname")
    Optional<Member> findByNickname(String nickname);

    @Modifying
    @Query("update Member set nickname =:nickname where mid = :mid")
    void changeNickname(String nickname, Long mid);

    @Modifying
    @Query("update Member set password = :password where mid = :mid")
    void changePassword(String password, Long mid);

    @Modifying
    @Query("update Movie set nickname = :nickname where nickname = :oldNickname")
    void modifyMovieWriterByNickname(@Param("nickname") String nickname, @Param("oldNickname") String oldNickname);

    @Modifying
    @Query("update Reply set replyer = :replyer where replyer = :oldNickname")
    void modifyReplyer(@Param("replyer") String replyer, @Param("oldNickname") String oldNickname);

}
