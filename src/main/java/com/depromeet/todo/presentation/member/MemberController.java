package com.depromeet.todo.presentation.member;

import com.depromeet.todo.application.member.MemberService;
import com.depromeet.todo.domain.member.Member;
import com.depromeet.todo.presentation.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

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
        return ApiResponse.successOf(memberResponse, "member");
    }

    @PutMapping("/members/me")
    public ApiResponse<MemberResponse> updateMe(
            @RequestHeader(required = false, name = "Authorization") String authorization,
            @ApiIgnore @ModelAttribute("memberId") Long memberId,
            @RequestBody @Valid MemberUpdateRequest memberUpdateRequest
    ) {
        Member member = memberService.updateMember(memberId, memberUpdateRequest.getName());
        MemberResponse memberResponse = memberResponseAssembler.toDisplayableMember(member);
        return ApiResponse.successOf(memberResponse, "member");
    }
}
