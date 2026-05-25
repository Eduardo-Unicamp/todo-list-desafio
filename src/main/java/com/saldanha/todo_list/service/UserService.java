package com.saldanha.todo_list.service;

import com.saldanha.todo_list.entity.Task;
import com.saldanha.todo_list.entity.User;
import com.saldanha.todo_list.exception.TaskNotFoundException;
import com.saldanha.todo_list.exception.UserNotFoundException;
import com.saldanha.todo_list.repository.TaskRepository;
import com.saldanha.todo_list.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.jackson.autoconfigure.JacksonProperties;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Data
@Service
public class UserService {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    //a fazer: adicionar validações do json
    //remove task

    public void addUser(User user){
        userRepository.save(user);
    }
    public List<User> getAllUsers(){
        List<User> allUsers = userRepository.findAll();
        if(allUsers.isEmpty()){throw new UserNotFoundException("Não há usuários cadastrados");}
        return allUsers;
    }
    public User getUser(UUID userId){
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }

    public Task getTask(UUID userId, UUID taskId){
        return taskRepository.findById(taskId).orElseThrow(TaskNotFoundException::new);
    }

    public void putUser(UUID userId, User userData){
        userData.setUserId(userId);
        userRepository.save(userData);
    }

    public void deleteUser(UUID userId){

        userRepository.deleteById(userId);
    }

    public void addTaskToUser(UUID userId, Task task ){
        JsonValidator jsonValidator = new JsonValidator();
        jsonValidator.taskValidation(task);

        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        user.addTask(task);
        userRepository.save(user);
    }

        public void changeTask(UUID userId, UUID taskId, Task newTask){
            newTask.setTaskId(taskId);
            taskRepository.save(newTask);

            User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
            user.addTask(newTask);
        }

    public void removeTaskFromUser(UUID userId, UUID taskId){
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Task task = taskRepository.findById(taskId).orElseThrow(TaskNotFoundException::new);
        user.removeTask(task);
        userRepository.save(user);
    }

    public void clearTaskFromUser(UUID userId){
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        user.setTasks(null);
        userRepository.save(user);
    }



}
