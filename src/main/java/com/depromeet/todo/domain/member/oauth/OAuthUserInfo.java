package com.depromeet.todo.domain.member.oauth;

import lombok.*;
import org.springframework.util.Assert;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OAuthUserInfo {
    @Enumerated(EnumType.STRING)
    @Column(name = "oauth_user_info_provider_type", nullable = false)
    private OAuthProviderType providerType;
    @Column(name = "oauth_user_info_provider_user_id", nullable = false)
    private String providerUserId;

    private OAuthUserInfo(OAuthProviderType providerType, String providerUserId) {
        this.providerType = providerType;
        this.providerUserId = providerUserId;
        this.validate();
    }

    public static OAuthUserInfo fromKakao(String providerUserId) {
        return new OAuthUserInfo(
                OAuthProviderType.KAKAO,
                providerUserId
        );
    }

    private void validate() {
        Assert.hasText(providerUserId, "'providerUserId' must not be null, empty or blank");
    }
}
