package swp_project.dna_service.dto.request;

import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
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
public class ServiceRequest {

    String userId;
    String serviceId;
    String service_name;
    String service_description;
    String service_category;
    String service_type;

    String imageUrl;
    Float test_price;

    int duration_days;
    int collection_method;

    boolean required_legal_document;

    boolean is_active;
}
