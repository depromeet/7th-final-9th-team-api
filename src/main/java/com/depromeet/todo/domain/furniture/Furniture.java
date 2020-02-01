package com.depromeet.todo.domain.furniture;

import com.depromeet.todo.domain.IdGenerator;
import com.depromeet.todo.domain.room.Room;
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
public class Furniture {
    @Id
    private Long furnitureId;
    private Long memberId;
    @ManyToOne
    private Room room;
    @Enumerated(EnumType.STRING)
    private FurnitureType furnitureType;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    private Furniture(Long furnitureId,
                      Long memberId,
                      Room room,
                      FurnitureType furnitureType,
                      LocalDateTime createdAt,
                      LocalDateTime updatedAt) {
        this.furnitureId = furnitureId;
        this.memberId = memberId;
        this.room = room;
        this.furnitureType = furnitureType;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.validate();
    }

    public static Furniture of(IdGenerator idGenerator,
                               Long memberId,
                               Room room,
                               FurnitureType furnitureType) {
        Assert.notNull(idGenerator, "'idGenerator' must not be null");

        return new Furniture(
                idGenerator.generate(),
                memberId,
                room,
                furnitureType,
                null,
                null
        );
    }

    private void validate() {
        Assert.notNull(furnitureId, "'furnitureId' must not be null");
        Assert.notNull(memberId, "'memberId' must not be null");
        Assert.notNull(furnitureType, "'furnitureType' must not be null");
        if (furnitureType == FurnitureType.UNKNOWN) {
            String availableTypes = FurnitureType.AVAILABLE_SET.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(", ", "[", "]"));
            throw new IllegalStateException("'type' must not be 'unknown'. Use " + availableTypes);
        }
    }
}
