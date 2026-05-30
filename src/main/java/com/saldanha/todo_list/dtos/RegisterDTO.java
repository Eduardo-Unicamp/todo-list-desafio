package com.saldanha.todo_list.dtos;

import com.saldanha.todo_list.entity.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDTO {
    @NotBlank
    private String firstName;
    private String lastName;
    @Email
    private String email;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotNull
    private UserRole role;
}
