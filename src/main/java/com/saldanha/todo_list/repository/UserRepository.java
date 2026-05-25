package com.saldanha.todo_list.repository;

import com.saldanha.todo_list.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
