package com.saldanha.todo_list.controller;

import com.saldanha.todo_list.dtos.TaskDTO;
import com.saldanha.todo_list.entity.Task;
import com.saldanha.todo_list.service.TaskService;
import com.saldanha.todo_list.service.UserService;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Data
@RequestMapping("todo-manager/tasks")
@RestController
public class TaskController {
    private final UserService userService;
    private final TaskService taskService;

    //---> /tasks level: GET(list) all tasks or POST a new task(owner is gotten from body)
    @GetMapping
    public ResponseEntity<List<Task>> listTasks(){
        return ResponseEntity.ok(taskService.listPublicTasks());
    }
    @GetMapping("/from/{userId}")
    public ResponseEntity<List<Task>> listTasks(@PathVariable UUID userId){
        return ResponseEntity.ok(taskService.listPublicAndThisUserTasks(userId));
    }


    @PostMapping()
    public ResponseEntity<Void> addTask( @Validated @RequestBody TaskDTO dto){
        taskService.addTask(dto);
        return ResponseEntity.status(201).build();
    }



    //---> /tasks/{taskId} level: GET an specific task by id, PUT(update) a task, DELETE a task

    @GetMapping("/{taskId}")
    public ResponseEntity<Task> getTask(@PathVariable UUID taskId){
        return ResponseEntity.status(200).body(taskService.getTask(taskId));
    }



    @PutMapping("/{taskId}")
    public ResponseEntity<String> putTask(@PathVariable UUID taskId, @Validated @RequestBody TaskDTO dto){
        taskService.updateTask(taskId,dto);
        return ResponseEntity.ok("Task changed");
    }



    @DeleteMapping("/{taskId}")
    public ResponseEntity<String> deleteTask( @PathVariable UUID taskId){
        taskService.deleteTask(taskId);
        return ResponseEntity.ok(String.format("Task %s removed",taskId));
    }





}
