package com.depromeet.todo.domain.task;

import com.depromeet.todo.domain.furniture.Furniture;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@ToString(exclude = "furniture")
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Task {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Long id;
    @Column
    private Long memberId;
    @ManyToOne
    @JoinColumn(name = "furniture_id")
    private Furniture furniture;
    @Enumerated(EnumType.STRING)
    private TaskState state;
    @Column
    private String contents;
    @Column
    private Integer displayOrder;
    @Column
    private LocalDateTime deadline;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    private Task(long memberId,
                 Furniture furniture,
                 TaskState state,
                 String contents,
                 Integer displayOrder,
                 LocalDateTime deadline) {
        this.memberId = memberId;
        this.furniture = furniture;
        this.state = state;
        this.contents = contents;
        this.displayOrder = displayOrder;
        this.deadline = deadline;
        this.createdAt = null;
        this.updatedAt = null;
    }

    public static Task of(long memberId,
                          Furniture furniture,
                          String contents,
                          int displayOrder) {
        LocalDateTime deadline = LocalDate.now().atTime(LocalTime.MAX);
        return new Task(memberId, furniture, TaskState.TODO, contents, displayOrder, deadline);
    }

    public void done() {
        state = TaskState.DONE;
    }

    public boolean isTodo() {
        return state.isTodo();
    }


    public boolean equalsTaskState(TaskState taskState) {
        return state.equals(taskState);
    }
}