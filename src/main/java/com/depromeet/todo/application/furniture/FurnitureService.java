package com.depromeet.todo.application.furniture;

import com.depromeet.todo.application.ResourceNotFoundException;
import com.depromeet.todo.domain.IdGenerator;
import com.depromeet.todo.domain.furniture.Furniture;
import com.depromeet.todo.domain.furniture.FurnitureRepository;
import com.depromeet.todo.domain.furniture.FurnitureType;
import com.depromeet.todo.domain.member.Member;
import com.depromeet.todo.domain.member.MemberRepository;
import com.depromeet.todo.domain.room.Room;
import com.depromeet.todo.domain.room.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Slf4j
@Service
@RequiredArgsConstructor
public class FurnitureService {
    private final MemberRepository memberRepository;
    private final RoomRepository roomRepository;
    private final IdGenerator idGenerator;
    private final FurnitureRepository furnitureRepository;

    @Transactional
    public Furniture createFurniture(Long memberId, Long roomId, FurnitureType furnitureType) {
        Assert.notNull(memberId, "'memberId' must not be null");
        Assert.notNull(furnitureType, "'furnitureType' must not be null");

        Member member = this.getMember(memberId);
        Room room = this.getRoom(member, roomId);
        Furniture furniture = Furniture.of(
                idGenerator,
                member,
                room,
                furnitureType
        );
        return furnitureRepository.save(furniture);
    }

    private Room getRoom(Member owner, Long roomId) {
        assert owner != null;
        assert roomId != null;
        return roomRepository.findByRoomIdAndOwner(roomId, owner)
                .orElseThrow(() -> {
                    log.warn("Room not found. roomId: {}, member: {}", roomId, owner);
                    return new ResourceNotFoundException("Room not found. roomId: " + roomId);
                });
    }

    private Member getMember(Long memberId) {
        assert memberId != null;
        return memberRepository.findById(memberId)
                .orElseThrow(() -> {
                    log.warn("Member not found. memberId: {}", memberId);
                    return new ResourceNotFoundException("Member not found. memberId: " + memberId);
                });
    }
}
