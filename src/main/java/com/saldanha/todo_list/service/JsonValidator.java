package com.saldanha.todo_list.service;

import com.saldanha.todo_list.entity.Task;
import com.saldanha.todo_list.enums.Priority;
import com.saldanha.todo_list.exception.EmptyRequiredFieldsException;
import com.saldanha.todo_list.exception.InvalidPriorityTagException;
import lombok.Data;

import java.util.Arrays;

@Data
public class JsonValidator {

    public Boolean taskValidation(Task task){
        return(assureValidEnumCategory(task) && assureRequiredFields(task));
    }



    private Boolean assureValidEnumCategory(Task task){
        //se o valor de priority nao esta contido nos conjunto de valores do enum
        if(!Arrays.stream(Priority.values()).toList().contains(task.getPriority())){
            throw new InvalidPriorityTagException();
        }
        return true;
    }

    private Boolean assureRequiredFields(Task task){
        if(task.getName()==null || task.getName().isEmpty() || task.getDone()==null){
            throw new EmptyRequiredFieldsException();
        }
        return true;
    }
}
