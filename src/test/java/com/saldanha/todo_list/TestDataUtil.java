package com.saldanha.todo_list;

import com.saldanha.todo_list.dtos.TaskDTO;
import com.saldanha.todo_list.entity.Task;
import com.saldanha.todo_list.entity.User;
import com.saldanha.todo_list.entity.UserRole;
import com.saldanha.todo_list.enums.Priority;
import com.saldanha.todo_list.enums.Status;
import com.saldanha.todo_list.infra.security.UserDetailsImp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TestDataUtil {

    // 1. Helper to create a dummy User (Needed for the owner/participants)
    public static User createDummyUser() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@email.com");
        user.setUsername("johndoe");
        user.setPassword("encryptedPassword123");
        user.setRole(UserRole.USER);
        return user;
    }

    // 2. Helper to create a dummy Task Entity
    public static Task createDummyTask() {
        User owner = createDummyUser();

        return new Task(
                null,                      // taskId
                "Complete unit tests",                   // title
                "Write JUnit tests for TaskService",     // description
                LocalDate.now().plusDays(7),             // dueDate (must be FutureOrPresent)
                Priority.HIGH,                           // priority
                Status.TODO,                             // status
                true,
                new ArrayList<>(),
                owner,                                   // owner
                new ArrayList<>()                        // participants (empty list)
        );
    }

    // 3. Helper to create a dummy TaskDTO
    public static TaskDTO createDummyTaskDTO() {
        User owner = createDummyUser();

        return new TaskDTO(
                null,                       // id
                "Complete unit tests",                   // title
                "Write JUnit tests for TaskController",  // description
                LocalDate.now().plusDays(7),             // dueDate
                Priority.HIGH,                           // priority
                Status.TODO,                             // status
                true,                                    // isPublic                 // owner
                new ArrayList<>()                        // participants
        );
    }

    public static List<Task> MockTaskList(){
        List<Task> taskList = new ArrayList<>();
        for(int i=0;i<10;i++){
            createDummyTask();
        }
        return taskList;
    }

    public static UserDetailsImp MockUserDetailsImp(){
        return new UserDetailsImp(createDummyUser());
    }
}