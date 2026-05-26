package com.saldanha.todo_list.controller;

import com.saldanha.todo_list.entity.User;
import com.saldanha.todo_list.service.UserService;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("todo-manager/users")
@Data
public class UserController {
    private final UserService userService;

    //---> /users level: GET all users or POST a new user

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping()
    public ResponseEntity<String> addUser(@RequestBody User user){
        userService.addUser(user);
        return ResponseEntity.status(201).body("User created");
    }

    //---> /users/userId level: GET an user, PUT(update) an user, DELETE an user
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable UUID userId){
        return ResponseEntity.ok(userService.getUser(userId));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<String> putUser(@PathVariable UUID userId,@RequestBody User user){
        userService.putUser(userId,user);
        return ResponseEntity.ok("User updated ");
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable UUID userId){
        userService.deleteUser(userId);
        return ResponseEntity.ok(String.format("User %s deleted",userId));
    }

}
