package imtiaz.sktech.mytutoron.mapper;

import imtiaz.sktech.mytutoron.model.domain.AssignmentCategory;
import imtiaz.sktech.mytutoron.model.dto.request.CreateAssignmentCategoryRequest;
import imtiaz.sktech.mytutoron.persistence.entity.AssignmentCategoryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AssignmentCategoryMapper {
    AssignmentCategory toDomain(AssignmentCategoryEntity assignmentCategoryEntity);

    AssignmentCategoryEntity toEntity(CreateAssignmentCategoryRequest createAssignmentCategoryRequest);
}
