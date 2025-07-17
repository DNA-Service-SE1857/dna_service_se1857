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
public class VietQRRequest {

    private String accountNo;
    private String accountName;
    private String acqId;
    private String amount;
    private String addInfo;
    private String template;

}
