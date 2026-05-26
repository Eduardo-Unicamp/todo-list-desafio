package com.saldanha.todo_list.service;

import com.saldanha.todo_list.entity.Task;
import com.saldanha.todo_list.entity.User;
import com.saldanha.todo_list.exception.TaskNotFoundException;
import com.saldanha.todo_list.exception.UserNotFoundException;
import com.saldanha.todo_list.repository.TaskRepository;
import com.saldanha.todo_list.repository.UserRepository;
import lombok.Data;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Data
@Service
public class UserService {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    //a fazer: adicionar validações do json
    //remove task

    public void addUser(User user) {
        userRepository.save(user);
    }

    public List<User> getAllUsers() {
        Sort sort = Sort.by("firstName").ascending();

        List<User> allUsers = userRepository.findAll(sort);
        if (allUsers.isEmpty()) {
            throw new UserNotFoundException("Não há usuários cadastrados");
        }
        return allUsers;
    }

    public User getUser(UUID userId) {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }

    public Task getTask(UUID taskId) {
        return taskRepository.findById(taskId).orElseThrow(TaskNotFoundException::new);
    }

    public void putUser(UUID userId, User userData) {
        userData.setUserId(userId);
        userRepository.save(userData);
    }

    public void deleteUser(UUID userId) {

        userRepository.deleteById(userId);
    }

    public void addTask(Task task) {
        addUser(task.getOwner());
        taskRepository.save(task);//A fazer:adicionar validações,logica etc
    }

    public void updateTask(UUID taskId, Task newTask) {
        //save or update owner as a User in out database
        putUser(newTask.getOwner().getUserId(),newTask.getOwner());
        //save or update all the participants
        if (!(newTask.getParticipants()==null ||newTask.getParticipants().isEmpty())) {
            for (User participant : newTask.getParticipants()) {
                putUser(participant.getUserId(),participant);
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
