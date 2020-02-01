package com.depromeet.todo.domain.room;

import com.depromeet.todo.domain.IdGenerator;
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

    private Room(Long roomId,
                 Long memberId,
                 RoomType type,
                 LocalDateTime createdAt,
                 LocalDateTime updatedAt) {
        this.roomId = roomId;
        this.memberId = memberId;
        this.type = type;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.validate();
    }

    public static Room of(IdGenerator idGenerator,
                          Long memberId,
                          RoomType type) {
        Assert.notNull(idGenerator, "'idGenerator' must not be null");

        return new Room(
                idGenerator.generate(),
                memberId,
                type,
                null,
                null
        );
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
