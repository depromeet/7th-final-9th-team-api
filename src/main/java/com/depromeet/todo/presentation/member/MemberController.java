package com.depromeet.todo.presentation.member;

import com.depromeet.todo.application.member.MemberService;
import com.depromeet.todo.domain.member.Member;
import com.depromeet.todo.presentation.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final MemberResponseAssembler memberResponseAssembler;

    @GetMapping("/members/me")
    public ApiResponse<MemberResponse> getMe(
            @RequestHeader(required = false, name = "Authorization") String authorization,
            @ApiIgnore @ModelAttribute("memberId") Long memberId
    ) {
        Member member = memberService.getMember(memberId);
        MemberResponse memberResponse = memberResponseAssembler.toDisplayableMember(member);
        return ApiResponse.successFrom(memberResponse);
    }

    @PostMapping("/members/login")
    public ApiResponse<LoginResponse> login(
            @RequestHeader(required = false, name = "Authorization") String authorization,
            @RequestBody LoginRequest loginRequest
    ) {
        return null;
    }

    @PostMapping("/members/login")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ApiResponse<LoginResponse> logout(
            @RequestHeader(required = false, name = "Authorization") String authorization
    ) {
        return null;
    }
}
