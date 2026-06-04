package com.saldanha.todo_list.Service;

import com.saldanha.todo_list.TestDataUtil;
import com.saldanha.todo_list.entity.Task;
import com.saldanha.todo_list.repository.TaskRepository;
import com.saldanha.todo_list.service.TaskService;
import com.saldanha.todo_list.service.mapper.TaskMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

class TaskServiceTest {

    @Mock
    TaskRepository taskRepository;
    @Mock
    TaskMapper taskMapper;

    @InjectMocks
    TaskService taskService;

    @BeforeEach
    void setUp() {
    }


    @Test
    void addTaskSuccess() {
    }

    @Test
    void updateTask() {
    }

    @Test
    void deleteTask() {
    }

    @Test
    void addFileToTask() {
    }
}