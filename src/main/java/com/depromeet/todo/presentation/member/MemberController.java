package com.depromeet.todo.presentation.member;

import com.depromeet.todo.application.member.MemberApplicationService;
import com.depromeet.todo.domain.member.Member;
import com.depromeet.todo.presentation.common.ApiResponse;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberController {
    private final MemberApplicationService memberApplicationService;
    private final MemberResponseAssembler memberResponseAssembler;

    @GetMapping("/members/me")
    public ApiResponse<MemberResponse> getMe(
            @ApiParam(name = "Authorization", required = true)
            @RequestHeader(required = false, name = "Authorization") String authorization,
            @ApiIgnore @ModelAttribute("memberId") Long memberId
    ) {
        Member member = memberApplicationService.getMember(memberId);
        MemberResponse memberResponse = memberResponseAssembler.toDisplayableMember(member);
        return ApiResponse.successFrom(memberResponse);
    }

    @PutMapping("/members/me")
    public ApiResponse<MemberResponse> updateMe(
            @ApiParam(name = "Authorization", required = true)
            @RequestHeader(required = false, name = "Authorization") String authorization,
            @ApiIgnore @ModelAttribute("memberId") Long memberId,
            @RequestBody @Valid MemberUpdateRequest memberUpdateRequest
    ) {
        Member member = memberApplicationService.updateMember(memberId, memberUpdateRequest.getName());
        MemberResponse memberResponse = memberResponseAssembler.toDisplayableMember(member);
        return ApiResponse.successFrom(memberResponse);
    }
}
