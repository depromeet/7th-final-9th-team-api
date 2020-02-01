package com.depromeet.todo.presentation.member;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
class MemberUpdateRequest {
    @NotBlank
    private String name;
}
