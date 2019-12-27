package com.depromeet.todo.application.member;

import com.depromeet.todo.application.Displayable;
import com.depromeet.todo.domain.member.Member;

public interface DisplayableMemberAssembler {
    Displayable toDisplayableMember(Member member);
}
