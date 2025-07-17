package swp_project.dna_service.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import swp_project.dna_service.entity.Dna_Service;
import swp_project.dna_service.entity.MedicalRecord;
import swp_project.dna_service.entity.OrderDetail;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Builder
public class TasksResponse {

    String id;
    String task_title;
    String task_description;
    String task_type;
    String status;
    Date dueDate;
    Date completedDate;
    String notes;
    Date createdAt;
    Date updatedAt;
    String dnaServiceId;
    String orderDetailId;
    String medicalRecordId;

}
