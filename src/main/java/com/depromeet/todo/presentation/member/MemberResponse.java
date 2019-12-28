package com.depromeet.todo.presentation.member;

import com.depromeet.todo.application.Displayable;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MemberResponse implements Displayable {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    private String providerType;
    private String providerUserId;
    //    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDateTime updatedAt;
}
