package com.depromeet.todo.presentation.member;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LoginResponse {
    private String accessToken;
    @JsonProperty("member")
    private MemberResponse memberResponse;
}
