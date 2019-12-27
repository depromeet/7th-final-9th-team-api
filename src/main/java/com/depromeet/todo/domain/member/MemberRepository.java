package com.depromeet.todo.domain.member;

import com.depromeet.todo.domain.member.oauth.OAuthUserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByOauthUserInfo(OAuthUserInfo oAuthUserInfo);
}
