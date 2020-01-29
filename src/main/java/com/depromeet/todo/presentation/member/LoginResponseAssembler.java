package com.depromeet.todo.presentation.member;

import com.depromeet.todo.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginResponseAssembler {
    private final MemberResponseAssembler memberResponseAssembler;

    public LoginResponse toLoginResponse(String accessToken, Member member) {
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setAccessToken(accessToken);
        loginResponse.setMemberResponse(memberResponseAssembler.toDisplayableMember(member));
        return loginResponse;
    }
}
