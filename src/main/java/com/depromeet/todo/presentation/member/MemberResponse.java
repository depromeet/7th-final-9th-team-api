package com.depromeet.todo.presentation.member;

import com.depromeet.todo.application.Displayable;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MemberResponse implements Displayable {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    private String name;
    private String profileImage;
    private String providerType;
    private String providerUserId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
