package com.depromeet.todo.domain.member;

import lombok.ToString;
import org.springframework.context.ApplicationEvent;
import org.springframework.util.Assert;

@ToString
public class MemberCreatedEvent extends ApplicationEvent {
    private static final long serialVersionUID = 4046744884312277246L;

    private final Long memberId;

    private MemberCreatedEvent(Object source, Long memberId) {
        super(source);

        Assert.notNull(memberId, "'memberId' must not be null");
        this.memberId = memberId;
    }

    public static MemberCreatedEvent of(Object source, Long memberId) {
        return new MemberCreatedEvent(source, memberId);
    }

    public Long getMemberId() {
        return memberId;
    }
}
