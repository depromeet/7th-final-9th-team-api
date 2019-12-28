package com.depromeet.todo.presentation.member;

import com.depromeet.todo.application.member.MemberService;
import com.depromeet.todo.domain.member.Member;
import com.depromeet.todo.presentation.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final MemberResponseAssembler memberResponseAssembler;

    @GetMapping("/members/me")
    public ApiResponse<MemberResponse> getMe(@ModelAttribute("memberId") Long memberId) {
        Member member = memberService.getMember(memberId);
        MemberResponse memberResponse = memberResponseAssembler.toDisplayableMember(member);
        return ApiResponse.successFrom(memberResponse);
    }
}
