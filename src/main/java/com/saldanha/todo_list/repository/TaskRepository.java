package com.saldanha.todo_list.repository;

import com.saldanha.todo_list.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {

    @Query("SELECT DISTINCT t from Task t LEFT JOIN t.participants p " +
            "WHERE t.owner.userId = :userId " +
            "OR p.userId = :userId " +
            "OR t.isPublic = true")
    public List<Task> listAvaiableTasksForUser(@Param("userId") UUID userId);

    List<Task> findByIsPublicTrue();
}
