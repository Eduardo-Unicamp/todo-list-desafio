package com.saldanha.todo_list.service;

import com.saldanha.todo_list.entity.Task;
import com.saldanha.todo_list.repository.TaskRepository;
import com.saldanha.todo_list.repository.UserRepository;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
@Data
public class TaskService {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    @GetMapping
    public List<Task> listPublicTasks(){
        return taskRepository.findAll();
    }

}
