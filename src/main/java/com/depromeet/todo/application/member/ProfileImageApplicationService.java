package com.depromeet.todo.application.member;

import com.depromeet.todo.domain.member.Member;
import com.depromeet.todo.domain.storage.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class ProfileImageApplicationService {
    private final StorageService storageService;
    private final MemberApplicationService memberApplicationService;

    public Member upload(Long memberId, MediaType mediaType, InputStream inputStream) {
        Assert.notNull(memberId, "'memberId' must not be null");
        Assert.notNull(mediaType, "'mediaType' must not be null");
        Assert.notNull(inputStream, "'inputStream' must not be null");

        String profileImageUrl = storageService.save(mediaType, inputStream);
        return memberApplicationService.updateProfileImage(memberId, profileImageUrl);
    }
}
