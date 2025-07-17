package swp_project.dna_service.mapper;


import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import swp_project.dna_service.dto.request.TasksRequest;
import swp_project.dna_service.dto.request.TestResultRequest;
import swp_project.dna_service.dto.response.TasksResponse;
import swp_project.dna_service.dto.response.TestResultResponse;
import swp_project.dna_service.entity.Tasks;
import swp_project.dna_service.entity.TestResult;

@Mapper(componentModel = "spring")
public interface TestResultMapper {

    TestResultResponse toTestResultResponse(TestResult entity);

    TestResult toTestResult(TestResultRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateTestResult(@MappingTarget TestResult entity, TestResultRequest request);
}
