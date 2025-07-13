package swp_project.dna_service.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Builder
public class TestResultRequest {

    String id ;

    String result_type ;
    String result_percentage ;
    String conclusion;
    String result_detail ;
    String result_file ;
    String tested_date ;
    String sample_id ;

}
