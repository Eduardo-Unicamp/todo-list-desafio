package com.saldanha.todo_list.service.mapper;

import com.saldanha.todo_list.dtos.UserDTO;
import com.saldanha.todo_list.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User dtoToUser(UserDTO userDTO);
}
