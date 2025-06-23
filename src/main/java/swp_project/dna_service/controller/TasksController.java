package swp_project.dna_service.controller;


import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.*;
import swp_project.dna_service.dto.ApiResponse;
import swp_project.dna_service.dto.request.TasksRequest;
import swp_project.dna_service.dto.response.TasksResponse;
import swp_project.dna_service.service.TasksService;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@EnableWebSecurity
@Slf4j
public class TasksController {

    TasksService tasksService;


    @PostMapping()
    public ApiResponse<TasksResponse> createTask(@RequestBody TasksRequest request ) {

        TasksResponse response = tasksService.createTask(request);

        return ApiResponse.<TasksResponse>builder()
                .code(200)
                .message("Service updated successfully")
                .result(response)
                .build();
    }

    // READ BY ID
    @GetMapping("/{id}")
    public ApiResponse<TasksResponse> getTaskById(@PathVariable String id) {
        TasksResponse response = tasksService.getTaskById(id);
        return ApiResponse.<TasksResponse>builder()
                .code(200)
                .message("Task fetched successfully")
                .result(response)
                .build();
    }

    // READ ALL
    @GetMapping
    public ApiResponse<List<TasksResponse>> getAllTasks() {
        List<TasksResponse> response = tasksService.getAllTasks();
        return ApiResponse.<List<TasksResponse>>builder()
                .code(200)
                .message("All tasks fetched successfully")
                .result(response)
                .build();
    }

    // READ BY ORDER_DETAIL_ID
    @GetMapping("/order-detail/{orderDetailId}")
    public ApiResponse<List<TasksResponse>> getTasksByOrderDetailId(@PathVariable String orderDetailId) {
        List<TasksResponse> response = tasksService.getTasksByOrderDetailId(orderDetailId);
        return ApiResponse.<List<TasksResponse>>builder()
                .code(200)
                .message("Tasks by OrderDetailId fetched successfully")
                .result(response)
                .build();
    }

    // READ BY DNA_SERVICE_ID
    @GetMapping("/dna-service/{dnaServiceId}")
    public ApiResponse<List<TasksResponse>> getTasksByDnaServiceId(@PathVariable String dnaServiceId) {
        List<TasksResponse> response = tasksService.getTasksByDnaServiceId(dnaServiceId);
        return ApiResponse.<List<TasksResponse>>builder()
                .code(200)
                .message("Tasks by DnaServiceId fetched successfully")
                .result(response)
                .build();
    }

    // UPDATE
    @PutMapping("/{id}")
    public ApiResponse<TasksResponse> updateTask(@PathVariable String id, @RequestBody TasksRequest request) {
        TasksResponse response = tasksService.updateTask(id, request);
        return ApiResponse.<TasksResponse>builder()
                .code(200)
                .message("Task updated successfully")
                .result(response)
                .build();
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteTask(@PathVariable String id) {
        tasksService.deleteTask(id);
        return ApiResponse.<Void>builder()
                .code(200)
                .message("Task deleted successfully")
                .build();
    }
}
