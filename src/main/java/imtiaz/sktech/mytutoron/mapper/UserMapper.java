package imtiaz.sktech.mytutoron.mapper;

import org.mapstruct.Mapper;
import imtiaz.sktech.mytutoron.model.domain.User;
import imtiaz.sktech.mytutoron.model.dto.request.CreateUserRequest;
import imtiaz.sktech.mytutoron.persistence.entity.UserEntity;

@Mapper(componentModel = "spring")
public interface UserMapper {
	User toDomain(UserEntity userEntity);

	UserEntity toEntity(CreateUserRequest request);
}
