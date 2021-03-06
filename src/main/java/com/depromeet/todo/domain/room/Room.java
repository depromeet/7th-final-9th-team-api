package com.depromeet.todo.domain.room;

import com.depromeet.todo.domain.furniture.Furniture;
import com.depromeet.todo.domain.task.Task;
import com.depromeet.todo.domain.task.TaskState;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@ToString(of = "id")
public class Room {

    @Id
    @Column(name = "room_id")
    private Long id;
    private Long memberId;
    @Enumerated(EnumType.STRING)
    private RoomType type;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Furniture> furniture = new ArrayList<>();

    public Room(Long id,
                Long memberId,
                RoomType type) {
        this.id = id;
        this.memberId = memberId;
        this.type = type;
        this.validate();
    }

    public static Room of(Long roomId, Long memberId, RoomType roomType, List<Furniture> furniture) {
        Room room = new Room(roomId, memberId, roomType);
        furniture.forEach(room::arrangeFurniture);
        return room;
    }

    private void arrangeFurniture(Furniture furniture) {
        this.furniture.add(furniture);
        if (!Objects.equals(furniture.getRoom(), this)) {
            furniture.addRoom(this);
        }
    }

    private void validate() {
        Assert.notNull(id, "'id' must not be null");
        Assert.notNull(memberId, "memberId");
        Assert.notNull(type, "'type' must not be null");
        if (type == RoomType.UNKNOWN) {
            String availableTypes = RoomType.AVAILABLE_TYPES.stream()
                                                            .map(RoomType::getName)
                                                            .collect(Collectors.joining(", ", "[", "]"));
            throw new IllegalRoomTypeException("'type' must not be UNKNOWN. Use " + availableTypes);
        }
    }

    public List<Task> getTasks(TaskState taskState) {
        return furniture.stream()
                        .flatMap(it -> it.getTasks(taskState)
                                         .stream())
                        .collect(Collectors.toList());
    }

    public List<Task> getTasks() {
        return furniture.stream()
                        .flatMap(it -> it.getTasks()
                                         .stream())
                        .collect(Collectors.toList());
    }
}
