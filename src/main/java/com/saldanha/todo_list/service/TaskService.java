package com.saldanha.todo_list.service;

import com.saldanha.todo_list.dtos.TaskDTO;
import com.saldanha.todo_list.entity.File;
import com.saldanha.todo_list.entity.Task;
import com.saldanha.todo_list.entity.User;
import com.saldanha.todo_list.exception.PassingInvalidUserIdException;
import com.saldanha.todo_list.exception.TaskNotFoundException;
import com.saldanha.todo_list.exception.UserNotFoundException;
import com.saldanha.todo_list.infra.security.UserDetailsImp;
import com.saldanha.todo_list.repository.FileRepository;
import com.saldanha.todo_list.repository.TaskRepository;
import com.saldanha.todo_list.repository.UserRepository;
import com.saldanha.todo_list.service.mapper.TaskMapper;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Data
public class TaskService {
    
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final FileRepository fileRepository;


    //for using user functions
    private final UserService userService;
    //mapper
    private final TaskMapper taskMapper;


    public List<Task> listPublicTasks(){
        return taskRepository.findByIsPublicTrue();
    }

    public List<Task> listUserTasks(UUID userId){
        //should list public, the ones this user owns and the ones he is a participant
        return taskRepository.listAvailableTasksForUser(userId);
    }


    public Task getTask(UUID taskId) {

        return taskRepository.findById(taskId).orElseThrow(TaskNotFoundException::new);
    }

    public void addTask(TaskDTO dto, UserDetailsImp userDetailsImp) {
        Task task = taskMapper.dtoToTask(dto);
        User owner = userDetailsImp.getUser();

        task.setOwner(owner);
        taskRepository.save(task);//A fazer:adicionar validações,logica etc
    }

    public void updateTask(UUID taskId, TaskDTO dto, UserDetailsImp userDetailsImp) {
        Task oldTask = taskRepository.findById(taskId).orElseThrow(TaskNotFoundException::new);

        Task newTask = taskMapper.dtoToTask(dto);
        newTask.setOwner(userDetailsImp.getUser());
        //save or update all the participants
        List<User> participants = new ArrayList<>();
        for(UUID participantId:dto.getParticipantIds()){
            User participant = userRepository.findById(participantId).orElseThrow(UserNotFoundException::new);
            participants.add(participant);
        }
        newTask.setParticipants(participants);

        //finally save task
        newTask.setTaskId(taskId);
        taskRepository.save(newTask);

    }

    public void deleteTask(UUID taskId) {
        taskRepository.deleteById(taskId);
    }

    public void addFileToTask(UUID taskId, String filePath){

        File newFile = new File(filePath);
        fileRepository.save(newFile);
        Task task = getTask(taskId);
        List<File> updatedFilesList = task.getFiles();
        updatedFilesList.add(newFile);
        task.setFiles(updatedFilesList);
        taskRepository.save(task);
    }



    public void addParticipant(UUID taskId, UUID participantId){
        Task task = taskRepository.findById(taskId).orElseThrow(TaskNotFoundException::new);
        User newParticipant = userRepository.findById(participantId).orElseThrow(UserNotFoundException::new);
        List<User> participants = task.getParticipants();
        participants.add(newParticipant);
        task.setParticipants(participants);
        taskRepository.save(task);

    }
}
