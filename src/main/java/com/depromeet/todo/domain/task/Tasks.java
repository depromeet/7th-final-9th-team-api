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
@ToString(of = "id")
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Tasks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private Integer ordered;
    @Column
    private LocalDateTime deadline;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    private Tasks(long memberId,
                  Furniture furniture,
                  TaskState state,
                  String contents,
                  Integer ordered,
                  LocalDateTime deadline) {
        this.memberId = memberId;
        this.furniture = furniture;
        this.state = state;
        this.contents = contents;
        this.ordered = ordered;
        this.deadline = deadline;
        this.createdAt = null;
        this.updatedAt = null;
    }

    public static Tasks of(long memberId,
                           Furniture furniture,
                           String contents,
                           int order) {
//        LocalDateTime deadline = LocalDate.now()
//                                          .atTime(LocalTime.MAX);
        LocalDateTime deadline = LocalDateTime.now();
        return new Tasks(memberId, furniture, TaskState.TODO, contents, order, deadline);
    }

    public void done() {
        state = TaskState.DONE;
    }

    public boolean isTodo() {
        return state == TaskState.TODO;
    }

    public enum TaskState {
        TODO, DONE
    }
}