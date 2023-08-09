package imtiaz.sktech.mytutoron.mapper;

import imtiaz.sktech.mytutoron.model.domain.Role;
import imtiaz.sktech.mytutoron.model.dto.request.CreateRoleRequest;
import imtiaz.sktech.mytutoron.persistence.entity.RoleEntity;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface RoleMapper {
    Role toDomain(RoleEntity roleEntity);
    RoleEntity toEntity(CreateRoleRequest request);
}
