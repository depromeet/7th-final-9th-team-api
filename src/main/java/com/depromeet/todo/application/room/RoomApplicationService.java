package com.depromeet.todo.application.room;

import com.depromeet.todo.application.BadRequestException;
import com.depromeet.todo.domain.IdGenerator;
import com.depromeet.todo.domain.ResourceNotFoundException;
import com.depromeet.todo.domain.member.Member;
import com.depromeet.todo.domain.member.MemberCreatedEvent;
import com.depromeet.todo.domain.member.MemberRepository;
import com.depromeet.todo.domain.room.Room;
import com.depromeet.todo.domain.room.RoomFactory;
import com.depromeet.todo.domain.room.RoomRepository;
import com.depromeet.todo.domain.room.RoomType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoomApplicationService {
    private final MemberRepository memberRepository;
    private final RoomRepository roomRepository;
    private final IdGenerator idGenerator;
    private final RoomFactory roomFactory;

    @Transactional
    public Room createRoom(Long memberId, RoomType roomType) {
        Assert.notNull(memberId, "'memberId' must not be null");
        Assert.notNull(roomType, "'roomType' must not be null");

        Member member = this.getMember(memberId);
        if (roomRepository.existsByMemberIdAndType(member.getMemberId(), roomType)) {
            log.warn("room already exists. owner: {}, roomType: {}", member, roomType);
            throw new BadRequestException("room already exists");
        }

        return roomFactory.createRoom(member.getMemberId(), roomType);
    }

    @Transactional(readOnly = true)
    public Room getRoom(Long memberId, Long roomId) {
        Assert.notNull(memberId, "'memberId' must not be null");
        Assert.notNull(roomId, "'roomId' must not be null");

        Member member = this.getMember(memberId);
        return roomRepository.findByRoomIdAndMemberId(roomId, member.getMemberId())
                .orElseThrow(() -> {
                    log.warn("Room not found. roomId: {}, member: {}", roomId, member);
                    return new ResourceNotFoundException("Room not found");
                });
    }

    @Transactional(readOnly = true)
    public Page<Room> getRooms(Long memberId, Pageable pageable) {
        Assert.notNull(memberId, "'memberId' must not be null");
        Assert.notNull(pageable, "'pageable' must not be null");

        Member member = this.getMember(memberId);
        return roomRepository.findByMemberId(member.getMemberId(), pageable);
    }

    private Member getMember(Long memberId) {
        assert memberId != null;
        return memberRepository.findById(memberId)
                .orElseThrow(() -> {
                    log.warn("Member not found. memberId: {}", memberId);
                    return new ResourceNotFoundException("Member not found. memberId: " + memberId);
                });
    }

    @EventListener
    @Transactional
    public void createInitialRooms(MemberCreatedEvent memberCreatedEvent) {
        Assert.notNull(memberCreatedEvent, "'memberCreatedEvent' must not be null");

        roomFactory.createInitialRooms(
                memberCreatedEvent.getMemberId()
        );
    }
}
