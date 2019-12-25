package com.depromeet.todo.domain.member.oauth;

public interface OAuthService {
    OAuthUserInfo getUserInfo(OAuthCredential oAuthCredential);
}
