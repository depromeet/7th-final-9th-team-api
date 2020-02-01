package com.depromeet.todo.domain.room;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Entity
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Room {
    @Id
    private Long roomId;
    private Long memberId;
    @Enumerated(EnumType.STRING)
    private RoomType type;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    Room(Long roomId,
         Long memberId,
         RoomType type) {
        this.roomId = roomId;
        this.memberId = memberId;
        this.type = type;
        this.validate();
    }

    private void validate() {
        Assert.notNull(roomId, "'roomId' must not be null");
        Assert.notNull(memberId, "memberId");
        Assert.notNull(type, "'type' must not be null");
        if (type == RoomType.UNKNOWN) {
            String availableTypes = RoomType.AVAILABLE_TYPES.stream()
                    .map(RoomType::getName)
                    .collect(Collectors.joining(", ", "[", "]"));
            throw new IllegalRoomTypeException("'type' must not be UNKNOWN. Use " + availableTypes);
        }
    }
}
