package com.depromeet.todo.application.room;

import com.depromeet.todo.application.ResourceNotFoundException;
import com.depromeet.todo.domain.IdGenerator;
import com.depromeet.todo.domain.member.Member;
import com.depromeet.todo.domain.member.MemberRepository;
import com.depromeet.todo.domain.room.Room;
import com.depromeet.todo.domain.room.RoomRepository;
import com.depromeet.todo.domain.room.RoomType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final MemberRepository memberRepository;
    private final RoomRepository roomRepository;
    private final IdGenerator idGenerator;

    @Transactional
    public Room createRoom(Long memberId, RoomType roomType) {
        Assert.notNull(memberId, "'memberId' must not be null");
        Assert.notNull(roomType, "'roomType' must not be null");

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found"));
        Room room = Room.of(
                idGenerator,
                member,
                roomType
        );
        return roomRepository.save(room);
    }

}
