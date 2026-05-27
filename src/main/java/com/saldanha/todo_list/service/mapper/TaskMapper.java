package com.saldanha.todo_list.service.mapper;

import com.saldanha.todo_list.dtos.TaskDTO;
import com.saldanha.todo_list.entity.Task;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    Task dtoToTask(TaskDTO taskDTO);
//MapStruct dependency automatically creates the class for this interface
// based on matching field names
}
