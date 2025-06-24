package swp_project.dna_service.mapper;


import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import swp_project.dna_service.dto.request.NotificationsRequest;
import swp_project.dna_service.dto.response.NotificationsResponse;
import swp_project.dna_service.entity.Notifications;


@Mapper(componentModel = "spring")
public interface NotificationsMapper {

    Notifications toNotifications(NotificationsRequest request);

    NotificationsResponse toNotificationsResponse(Notifications notifications);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateNotifications(@MappingTarget Notifications notifications, NotificationsRequest request);
}
