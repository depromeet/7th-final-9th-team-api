package com.depromeet.todo.domain.furniture;

import com.depromeet.todo.domain.room.Room;
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
import java.util.stream.Collectors;

@Entity
@Getter
@ToString(exclude = {"room", "tasks"})
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Furniture {

    public static final int ORDER_OF_INCREASE = 1;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "furniture_id")
    private Long id;
    private Long memberId;
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;
    @Enumerated(EnumType.STRING)
    private FurnitureType furnitureType;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "furniture", cascade = CascadeType.ALL)
    private List<Task> tasks = new ArrayList<>();

    private Furniture(Long memberId,
                      Room room,
                      FurnitureType furnitureType) {
        this(null, memberId, room, furnitureType, null, null);
    }

    private Furniture(Long id,
                      Long memberId,
                      Room room,
                      FurnitureType furnitureType,
                      LocalDateTime createdAt,
                      LocalDateTime updatedAt) {
        this.id = id;
        this.memberId = memberId;
        this.room = room;
        this.furnitureType = furnitureType;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        validate();
    }

    public static Furniture of(Long memberId, Room room, FurnitureType furnitureType) {
        return new Furniture(memberId, room, furnitureType);
    }

    public static Furniture of(Long memberId, FurnitureType furnitureType) {
        return new Furniture(memberId, null, furnitureType);
    }

    private void validate() {
        Assert.notNull(memberId, "'memberId' must not be null");
        Assert.notNull(furnitureType, "'furnitureType' must not be null");
        if (furnitureType == FurnitureType.UNKNOWN) {
            String availableTypes = FurnitureType.AVAILABLE_SET.stream()
                                                               .map(String::valueOf)
                                                               .collect(Collectors.joining(", ", "[", "]"));
            throw new IllegalStateException("'type' must not be 'unknown'. Use " + availableTypes);
        }
    }

    public Task registerTask(Long memberId, String contents) {
        int order = this.tasks.size() + ORDER_OF_INCREASE;
        Task task = Task.of(memberId, this, contents, order);
        this.tasks.add(task);
        return task;
    }

    public void addRoom(Room room) {
        this.room = room;
        if(!room.getFurniture().contains(this)){
            room.getFurniture().add(this);
        }
    }

    public boolean hasAuthority(Long memberId) {
        return this.memberId.equals(memberId);
    }

    public List<Task> getTasks(TaskState taskState) {
        return tasks.stream()
             .filter(it -> it.equalsTaskState(taskState))
             .collect(Collectors.toList());
    }

    public List<Task> getTasks() {
        return new ArrayList<>(tasks);
    }
}
