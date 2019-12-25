package com.depromeet.todo.domain.member;

import com.depromeet.todo.domain.IdGenerator;
import com.depromeet.todo.domain.member.oauth.OAuthUserInfo;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.Assert;

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
                        name = "idx_unique_Member_provider_type_Member_provider_user_id",
                        columnList = "oauth_user_info_provider_type, oauth_user_info_provider_user_id",
                        unique = true
                )
        }
)
public class Member {
    @Id
    private Long memberId;
    @Embedded
    private OAuthUserInfo oauthUserInfo; // XXX: 필드 이름을 'oAuthUserInfo' 로 쓰면, spring data jpa query 가 잘 생성되지 않음
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    public static Member of(IdGenerator idGenerator, OAuthUserInfo oAuthUserInfo) {
        Assert.notNull(idGenerator, "'idGenerator' must not be null");

        Member member = new Member();
        member.memberId = idGenerator.generate();
        member.oauthUserInfo = oAuthUserInfo;

        member.validate();
        return member;
    }

    private void validate() {
        Assert.notNull(memberId, "'memberId' must not be null");
        Assert.notNull(oauthUserInfo, "'oAuthUserInfo' must not be null");
    }
}
