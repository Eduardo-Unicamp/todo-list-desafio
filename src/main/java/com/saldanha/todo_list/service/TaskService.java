package com.saldanha.todo_list.service;

import com.saldanha.todo_list.entity.Task;
import com.saldanha.todo_list.entity.User;
import com.saldanha.todo_list.exception.TaskNotFoundException;
import com.saldanha.todo_list.repository.TaskRepository;
import com.saldanha.todo_list.repository.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.UUID;

@Service
@Data
public class TaskService {
    
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    //for using user functions
    private final UserService userService;

    @GetMapping
    public List<Task> listPublicTasks(){
        return taskRepository.findAll();
    }

    public Task getTask(UUID taskId) {
        return taskRepository.findById(taskId).orElseThrow(TaskNotFoundException::new);
    }

    public void addTask(Task task) {
        userService.addUser(task.getOwner());
        taskRepository.save(task);//A fazer:adicionar validações,logica etc
    }

    public void updateTask(UUID taskId, Task newTask) {
        //save or update owner as a User in out database
        userService.putUser(newTask.getOwner().getUserId(),newTask.getOwner());
        //save or update all the participants
        if (!(newTask.getParticipants()==null ||newTask.getParticipants().isEmpty())) {
            for (User participant : newTask.getParticipants()) {
                userService.putUser(participant.getUserId(),participant);
            }
        }
        //finally save task
        newTask.setTaskId(taskId);
        taskRepository.save(newTask);

    }

    public void deleteTask(UUID taskId) {
        taskRepository.deleteById(taskId);
    }
    
    
    
}
