package com.saldanha.todo_list.service;

import com.saldanha.todo_list.dtos.TaskDTO;
import com.saldanha.todo_list.entity.Task;
import com.saldanha.todo_list.entity.User;
import com.saldanha.todo_list.exception.PassingInvalidUserIdException;
import com.saldanha.todo_list.exception.TaskNotFoundException;
import com.saldanha.todo_list.repository.TaskRepository;
import com.saldanha.todo_list.repository.UserRepository;
import com.saldanha.todo_list.service.mapper.TaskMapper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Data
public class TaskService {
    
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;


    //for using user functions
    private final UserService userService;
    //mapper
    private final TaskMapper taskMapper;


    public List<Task> listPublicTasks(){
        return taskRepository.findByIsPublicTrue();
    }

    public List<Task> listPublicAndThisUserTasks(UUID userId){
        //should list public, the ones this user owns and the ones he is a participant
        return taskRepository.listAvaiableTasksForUser(userId);
    }


    public Task getTask(UUID taskId) {

        return taskRepository.findById(taskId).orElseThrow(TaskNotFoundException::new);
    }

    public void addTask(TaskDTO dto) {
        Task task = taskMapper.dtoToTask(dto);
        updateOwnership(task.getOwner());
        taskRepository.save(task);//A fazer:adicionar validações,logica etc
    }

    private void updateOwnership(User user){    //if:
        //(passes user without id field) -> save as new user
        //(passes user with a previously registered id -> update info for that user
        //passes user with an id not registered -> returns an exception

        if(user.getUserId()==null){//se nao passou id save atualiza se existe ou cria se nao
            userRepository.save(user);
        }
        User userIfAlreadyExists= userRepository.findById(user.getUserId()).orElseThrow(PassingInvalidUserIdException::new);
        userRepository.save(userIfAlreadyExists);
    }


    public void updateTask(UUID taskId, TaskDTO dto) {
        Task newTask = taskMapper.dtoToTask(dto);
        //save or update owner as a User in the database
        User bodyUser = newTask.getOwner();
        updateOwnership(bodyUser);
        //save or update all the participants
        if (!(newTask.getParticipants()==null ||newTask.getParticipants().isEmpty())) {
            for (User participant : newTask.getParticipants()) {
                updateOwnership(participant);
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
