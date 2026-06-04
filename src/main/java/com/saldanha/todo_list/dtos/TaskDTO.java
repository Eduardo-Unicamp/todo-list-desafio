package com.saldanha.todo_list.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.saldanha.todo_list.entity.User;
import com.saldanha.todo_list.enums.Priority;
import com.saldanha.todo_list.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {

    private UUID id;

    @NotEmpty(message = "Não deve ser null")
    private String title;

    private String description;

    @NotNull(message = "Não deve ser null")
    @FutureOrPresent(message = "Não deve ser null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dueDate;

    @NotNull(message = "Não deve ser null")
    private Priority priority;//LOW, MEDIUM, HIGH
    @NotNull(message = "Não deve ser null")
    private Status status;//DONE,DOING,TODO

    @NotNull
    private Boolean isPublic;




    @JsonProperty("participants")
    private List<UUID> participantIds = new ArrayList<>();



    @AssertTrue(message = "Status field must be filled with DONE,DOING or TODO")
    @JsonIgnore
    public boolean isStatusEnum(){
        if(priority==null){return false;}
        return Arrays.asList(Status.values()).contains(status);
    }

    @AssertTrue(message = "Priority field must be filled with LOW, MEDIUM or HIGH")
    @JsonIgnore
    public boolean isPriorityEnum(){
        if(priority==null){return false;}//cause @NotNull may be performed after this the validation order is not guaranteed
        return Arrays.asList(Priority.values()).contains(priority);
    }
}
