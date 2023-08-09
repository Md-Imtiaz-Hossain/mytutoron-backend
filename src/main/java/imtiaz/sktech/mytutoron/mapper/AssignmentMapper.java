package imtiaz.sktech.mytutoron.mapper;

import imtiaz.sktech.mytutoron.model.domain.Assignment;
import imtiaz.sktech.mytutoron.model.dto.request.CreateAssignmentRequest;
import imtiaz.sktech.mytutoron.persistence.entity.AssignmentEntity;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface AssignmentMapper {
    Assignment entityToDomain(AssignmentEntity assignmentEntity);

    AssignmentEntity domainToResponse(CreateAssignmentRequest createAssignmentRequest);
}