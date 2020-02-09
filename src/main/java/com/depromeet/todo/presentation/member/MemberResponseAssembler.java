package com.depromeet.todo.presentation.member;

import com.depromeet.todo.application.member.DisplayableMemberAssembler;
import com.depromeet.todo.domain.member.Member;
import org.springframework.stereotype.Component;

@Component
public class MemberResponseAssembler implements DisplayableMemberAssembler {
    @Override
    public MemberResponse toDisplayableMember(Member member) {
        if (member == null) {
            return null;
        }
        MemberResponse memberResponse = new MemberResponse();
        memberResponse.setId(member.getMemberId());
        memberResponse.setName(member.getName());
        memberResponse.setProfileImage(member.getProfileImage());
        memberResponse.setProviderType(member.getOauthUserInfo().getProviderType().name());
        memberResponse.setProviderUserId(member.getOauthUserInfo().getProviderUserId());
        memberResponse.setCreatedAt(member.getCreatedAt());
        memberResponse.setUpdatedAt(member.getUpdatedAt());
        return memberResponse;
    }
}
