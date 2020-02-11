package com.depromeet.todo.domain.task;

import com.depromeet.todo.domain.furniture.Furniture;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Tasks {

    public static final int DEADLINE_DAY_TO_INCREMENT = 1;
    public static final int DEADLINE_HOUR = 0;
    public static final int DEADLINE_MIN = 0;
    @Id
    @GeneratedValue
    private long id;
    private long memberId;
    @ManyToOne
    @JoinColumn(name = "furniture_id", nullable = false)
    private Furniture furniture;
    private TaskState state;
    private String contents;
    private Integer order;
    private LocalDateTime deadline;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    private Tasks(long memberId,
                  Furniture furniture,
                  TaskState state,
                  String contents,
                  Integer order,
                  LocalDateTime deadline) {
        this.memberId = memberId;
        this.furniture = furniture;
        this.state = state;
        this.contents = contents;
        this.order = order;
        this.deadline = deadline;
    }

    public static Tasks create(long memberId,
                               Furniture furniture,
                               String contents,
                               int order) {
        LocalDateTime deadline = LocalDate.now()
                                          .plusDays(DEADLINE_DAY_TO_INCREMENT)
                                          .atTime(DEADLINE_HOUR, DEADLINE_MIN);
        return new Tasks(memberId, furniture, TaskState.TODO, contents, order, deadline);
    }

    public void done() {
        state = TaskState.DONE;
    }

    public enum TaskState {
        TODO, DONE
    }
}