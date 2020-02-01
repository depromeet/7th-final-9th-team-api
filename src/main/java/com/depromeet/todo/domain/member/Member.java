package com.depromeet.todo.domain.member;

import com.depromeet.todo.domain.IdGenerator;
import com.depromeet.todo.domain.member.oauth.OAuthUserInfo;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Table(
        name = "Member",
        indexes = {
                @Index(
                        name = "idx_unique_Member_provider_type_provider_user_id",
                        columnList = "provider_type, provider_user_id",
                        unique = true
                )
        }
)
public class Member {
    @Id
    private Long memberId;
    @Embedded
    private OAuthUserInfo oauthUserInfo; // XXX: 필드 이름을 'oAuthUserInfo' 로 쓰면, spring data jpa query 가 잘 생성되지 않음
    private String name;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    private Member(Long memberId, OAuthUserInfo oAuthUserInfo) {
        this.memberId = memberId;
        this.oauthUserInfo = oAuthUserInfo;
        this.name = null;
    }

    private Member(IdGenerator idGenerator, OAuthUserInfo oAuthUserInfo) {
        Assert.notNull(idGenerator, "'idGenerator' must not be null");
        Assert.notNull(oAuthUserInfo, "'oAuthUserInfo' must not be null");

        this.memberId = idGenerator.generate();
        this.oauthUserInfo = oAuthUserInfo;
        this.name = null;
        this.validate();
    }

    public static Member of(IdGenerator idGenerator, OAuthUserInfo oAuthUserInfo) {
        return new Member(idGenerator, oAuthUserInfo);
    }

    public Member updateName(String name) {
        if (StringUtils.isEmpty(name)) {
            throw new IllegalArgumentException("'name' must not be null or empty");
        }
        this.name = name;
        this.validate();
        return this;
    }

    private void validate() {
        Assert.notNull(memberId, "'memberId' must not be null");
        Assert.notNull(oauthUserInfo, "'oAuthUserInfo' must not be null");
    }
}
