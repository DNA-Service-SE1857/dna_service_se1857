package swp_project.dna_service.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Builder
public class TasksRequest {

    String taskTitle;
    String taskDescription;
    String taskType;
    String status;
    Date dueDate;
    Date completedDate;
    String notes;

}
