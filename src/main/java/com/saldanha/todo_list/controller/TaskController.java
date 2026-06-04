package com.saldanha.todo_list.controller;

import com.saldanha.todo_list.dtos.TaskDTO;
import com.saldanha.todo_list.entity.File;
import com.saldanha.todo_list.entity.Task;
import com.saldanha.todo_list.entity.User;
import com.saldanha.todo_list.exception.FileUploadFailedException;
import com.saldanha.todo_list.infra.security.UserDetailsImp;
import com.saldanha.todo_list.repository.FileRepository;
import com.saldanha.todo_list.service.FileStorageService;
import com.saldanha.todo_list.service.TaskService;
import com.saldanha.todo_list.service.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("todo-manager/tasks")
@RestController
public class TaskController {
    private final UserService userService;
    private final TaskService taskService;
    private final FileStorageService fileStorageService;
    private final FileRepository fileRepository;

    //---> /tasks level: GET(list) all tasks or POST a new task(owner is gotten from body)
    @GetMapping
    public ResponseEntity<List<Task>> listTasks(){
        return ResponseEntity.ok(taskService.listPublicTasks());
    }
    @GetMapping("/my-tasks")
    public ResponseEntity<List<Task>> listTasks(@AuthenticationPrincipal UserDetailsImp userDetailsImp){
        User user = userDetailsImp.getUser();
        return ResponseEntity.ok(taskService.listUserTasks(user.getUserId()));
    }


    @PostMapping()
    public ResponseEntity<Void> addTask( @Validated @RequestBody TaskDTO dto, @AuthenticationPrincipal UserDetailsImp userDetailsImp){
        taskService.addTask(dto,userDetailsImp);
        return ResponseEntity.status(201).build();
    }



    //---> /tasks/{taskId} level: GET an specific task by id, PUT(update) a task, DELETE a task

    @GetMapping("/{taskId}")
    public ResponseEntity<Task> getTask(@PathVariable UUID taskId){
        return ResponseEntity.status(200).body(taskService.getTask(taskId));
    }



    @PutMapping("/{taskId}")
    public ResponseEntity<String> putTask(
            @PathVariable UUID taskId,
            @Validated @RequestBody TaskDTO dto,
            @AuthenticationPrincipal UserDetailsImp userDetailsImp)
    {
        taskService.updateTask(taskId,dto,userDetailsImp);
        return ResponseEntity.ok("Task changed");
    }



    @DeleteMapping("/{taskId}")
    public ResponseEntity<String> deleteTask( @PathVariable UUID taskId){
        taskService.deleteTask(taskId);
        return ResponseEntity.ok(String.format("Task %s removed",taskId));
    }

    @PostMapping("/{taskId}")
    public ResponseEntity<String> uploadFile(
            @PathVariable UUID taskId,
            @RequestParam("file")MultipartFile file)
    {
        try {
            String filePath = fileStorageService.uploadFile(file,taskId);
            taskService.addFileToTask(taskId,filePath);

            return ResponseEntity.status(201).body("Arquivo salvo com sucesso");
        }catch (Exception e) {//temporario, dar um jeito de botar no GLobalHandler
            return ResponseEntity.internalServerError().body("Erro no upload: " + e.getMessage());
        }


    }
    @GetMapping("{taskId}/attachment-url")
    public ResponseEntity<Object> getAttachmentUrl(@PathVariable UUID taskId){
        try {
            List<String> filePaths = new ArrayList<>();
           filePaths = taskService.getTask(taskId).getFiles().stream().map(File::getPath).toList();

           List<String> temporaryUrls = new ArrayList<>();
           for(String filePath:filePaths){
                temporaryUrls.add(fileStorageService.generateSignedUrl(filePath));
            }
           return ResponseEntity.ok(temporaryUrls);

        }catch (Exception e){
            return ResponseEntity.internalServerError().body("Erro ao gerar link: " + e.getMessage());
        }

    }


    @PostMapping("{taskId}/{userId}")
    public ResponseEntity<Object> addParticipantToTask(
            @PathVariable UUID taskId, @PathVariable UUID userId
    )
    {
        taskService.addParticipant(taskId,userId);
        return ResponseEntity.ok().build();
    }



}
