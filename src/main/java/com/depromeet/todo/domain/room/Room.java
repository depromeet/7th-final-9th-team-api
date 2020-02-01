package com.depromeet.todo.domain.room;

import com.depromeet.todo.domain.IdGenerator;
import com.depromeet.todo.domain.member.Member;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.Assert;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
@Getter
@ToString
@EqualsAndHashCode
@EntityListeners(AuditingEntityListener.class)
public class Room {
    @Id
    private Long roomId;
    @ManyToOne
    private Member owner;
    private String name;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    public Room(Long roomId,
                Member owner,
                String name,
                LocalDateTime createdAt,
                LocalDateTime updatedAt) {
        this.roomId = roomId;
        this.owner = owner;
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.validate();
    }

    public static Room of(IdGenerator idGenerator,
                          Member owner,
                          String name) {
        Assert.notNull(idGenerator, "'idGenerator' must not be null");

        return new Room(
                idGenerator.generate(),
                owner,
                name,
                null,
                null
        );
    }

    private void validate() {
        Assert.notNull(roomId, "'roomId' must not be null");
        Assert.notNull(owner, "'owner' must not be null");
        Assert.hasText(name, "'name' must not be null, empty or blank");
    }
}
