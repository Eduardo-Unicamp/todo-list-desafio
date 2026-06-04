package com.saldanha.todo_list.repository;

import com.saldanha.todo_list.TestDataUtil;
import com.saldanha.todo_list.entity.Task;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

@DataJpaTest
@ActiveProfiles("test")
class TaskRepositoryTest {
    @Autowired
     EntityManager entityManager;
    @Autowired
     TaskRepository taskRepository;

    @Test
    @DisplayName("Shoud return list of tasks succefully")
    void listAvailableTasksForUserCase1() {
        //initiating db with a task
        Task task = TestDataUtil.createDummyTask();
        addTaskToDB(task);

        List<Task> result = taskRepository.listAvailableTasksForUser(task.getOwner().getUserId());

        assertThat(result.contains(task));
    }




    private void addTaskToDB(Task task){
        entityManager.persist(task.getOwner());
        taskRepository.save(task);
    }




}