package com.saldanha.todo_list.service;

import com.saldanha.todo_list.dtos.UserDTO;
import com.saldanha.todo_list.entity.Task;
import com.saldanha.todo_list.entity.User;
import com.saldanha.todo_list.exception.TaskNotFoundException;
import com.saldanha.todo_list.exception.UserNotFoundException;
import com.saldanha.todo_list.repository.TaskRepository;
import com.saldanha.todo_list.repository.UserRepository;
import com.saldanha.todo_list.service.mapper.UserMapper;
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
    //MapStruct
    private final UserMapper userMapper;


    public void addUser(UserDTO dto){
        User user = userMapper.dtoToUser(dto);
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



    public void putUser(UUID userId, UserDTO dto) {
        User user = userMapper.dtoToUser(dto);
        user.setUserId(userId);
        userRepository.save(user);
    }

    public void deleteUser(UUID userId) {

        userRepository.deleteById(userId);
    }



}
