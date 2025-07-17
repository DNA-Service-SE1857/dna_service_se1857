package swp_project.dna_service.dto.request;


import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Builder
public class TasksRequest {

    String task_title;
    String task_description;
    String task_type;
    String status;
    Date dueDate;
    Date completedDate;
    String notes;

    String dnaServiceId ;
    String orderDetailId ;
    String medicalRecordId ;

}
