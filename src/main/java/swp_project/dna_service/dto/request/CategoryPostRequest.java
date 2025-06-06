package swp_project.dna_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryPostRequest {
    @NotBlank(message = "Category name cannot be blank")
    private String categoryName;
    
    private Boolean isActive;
}
