package com.saldanha.todo_list.controller;

import com.saldanha.todo_list.entity.Task;
import com.saldanha.todo_list.entity.User;
import com.saldanha.todo_list.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@Data
@RequestMapping("todo/task")
@RestController
public class TaskController {
    private final UserService userService;

    @PostMapping()
    public ResponseEntity<String> addUser(@RequestBody User user){
        userService.addUser(user);
        return ResponseEntity.status(201).body("User created");
    }

    @PostMapping("/{userId}")
    public ResponseEntity<String> addTask(@PathVariable UUID userId, @RequestBody Task task){
        userService.addTaskToUser(userId,task);
        return ResponseEntity.status(201).body(String.format("Task successfully added to user %s",userId));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable UUID userId){
        return ResponseEntity.ok(userService.getUser(userId));
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{userId}/{taskId}")
    public ResponseEntity<Task> getTask(@PathVariable UUID userId, @PathVariable UUID taskId){
        return ResponseEntity.status(200).body(userService.getTask(userId, taskId));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<String> putUser(@PathVariable UUID userId,@RequestBody User user){
        userService.putUser(userId,user);
        return ResponseEntity.ok("User updated ");
    }

    @PutMapping("/{userId}/{taskId}")
    public ResponseEntity<String> putTask(
            @PathVariable UUID userId,@PathVariable UUID taskId, @RequestBody Task newTask
            ){
        userService.changeTask(userId, taskId,newTask);
        return ResponseEntity.ok("Task changed");
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable UUID userId){
        userService.deleteUser(userId);
        return ResponseEntity.ok(String.format("User %s deleted",userId));
    }

    @DeleteMapping("/{userId}/{taskId}")
    public ResponseEntity<String> deleteTask(@PathVariable UUID userId, @PathVariable UUID taskId){
        userService.removeTaskFromUser(userId,taskId);
        return ResponseEntity.ok(String.format("Task %s removed",taskId));
    }

    @DeleteMapping("/{userId}/tasks")
    public ResponseEntity<String> clearTasks(@PathVariable UUID userId){
        userService.clearTaskFromUser(userId);
        return ResponseEntity.ok(String.format("All tasks from %s removed",userId));
    }


    //add patch maybe??

}
