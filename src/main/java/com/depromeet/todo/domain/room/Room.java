package com.depromeet.todo.domain.room;

import com.depromeet.todo.domain.IdGenerator;
import com.depromeet.todo.domain.member.Member;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Room {
    @Id
    private Long roomId;
    @ManyToOne
    private Member owner;
    @Enumerated(EnumType.STRING)
    private RoomType type;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    private Room(Long roomId,
                 Member owner,
                 RoomType type,
                 LocalDateTime createdAt,
                 LocalDateTime updatedAt) {
        this.roomId = roomId;
        this.owner = owner;
        this.type = type;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.validate();
    }

    public static Room of(IdGenerator idGenerator,
                          Member owner,
                          RoomType type) {
        Assert.notNull(idGenerator, "'idGenerator' must not be null");

        return new Room(
                idGenerator.generate(),
                owner,
                type,
                null,
                null
        );
    }

    private void validate() {
        Assert.notNull(roomId, "'roomId' must not be null");
        Assert.notNull(owner, "'owner' must not be null");
        Assert.notNull(type, "'type' must not be null");
    }
}
