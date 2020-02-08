package com.depromeet.todo.presentation.member;

import com.depromeet.todo.application.BadRequestException;
import com.depromeet.todo.application.member.ProfileImageApplicationService;
import com.depromeet.todo.domain.member.Member;
import com.depromeet.todo.presentation.common.ApiResponse;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import java.io.IOException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProfileImageController {
    private final ProfileImageApplicationService profileImageApplicationService;
    private final MemberResponseAssembler memberResponseAssembler;

    @PostMapping("/members/me/profile-image")
    public ApiResponse<MemberResponse> uploadProfileImage(
            @ApiParam(name = "Authorization", required = true)
            @RequestHeader(required = false, name = "Authorization") String authorization,
            @ApiIgnore @ModelAttribute("memberId") Long memberId,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        String contentType = file.getContentType();
        if (StringUtils.isEmpty(contentType)) {
            throw new BadRequestException("'contentType' must not be null or empty");
        }
        MediaType mediaType = MediaType.parseMediaType(contentType);
        Member member = profileImageApplicationService.upload(memberId, mediaType, file.getInputStream());
        MemberResponse memberResponse = memberResponseAssembler.toDisplayableMember(member);
        return ApiResponse.successFrom(memberResponse);
    }
}
