package com.depromeet.todo.domain.member.oauth;

import lombok.Value;

@Value(staticConstructor = "from")
public class OAuthAccessToken implements OAuthCredential {
    private final String value;

    @Override
    public String getCredential() {
        return value;
    }
}
