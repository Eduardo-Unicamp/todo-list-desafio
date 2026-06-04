package com.saldanha.todo_list.entity;

import com.saldanha.todo_list.enums.Priority;
import com.saldanha.todo_list.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="task_id")
    private UUID taskId;
    private String title;

    @Column(length = 2000)
    private String description;
    @Column(name = "due_date")
    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    private Priority priority;//LOW, MEDIUM, HIGH
    @Enumerated(EnumType.STRING)
    private Status status;//DONE,DOING,TODO

    private Boolean isPublic;

    @OneToMany
    private List<File> files = new ArrayList<>();

    @ManyToOne
    private User owner;

    @ManyToMany
    @JoinTable(
            name = "task_participants",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> participants = new ArrayList<>();

}
