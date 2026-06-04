package com.saldanha.todo_list.repository;

import com.saldanha.todo_list.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FileRepository extends JpaRepository<File, UUID> {
}
