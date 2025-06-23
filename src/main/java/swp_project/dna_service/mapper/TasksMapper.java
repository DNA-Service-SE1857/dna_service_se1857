package swp_project.dna_service.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import swp_project.dna_service.dto.request.TasksRequest;
import swp_project.dna_service.dto.response.TasksResponse;
import swp_project.dna_service.entity.Tasks;

@Mapper(componentModel = "spring")
public interface TasksMapper {

     TasksResponse toResponse(Tasks entity);

     Tasks toEntity(TasksRequest request);

     void updateTasks(@MappingTarget Tasks tasks, TasksRequest request);
}
