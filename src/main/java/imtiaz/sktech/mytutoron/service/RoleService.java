package imtiaz.sktech.mytutoron.service;

import lombok.RequiredArgsConstructor;
import imtiaz.sktech.mytutoron.exception.custom.NotFoundException;
import imtiaz.sktech.mytutoron.mapper.RoleMapper;
import imtiaz.sktech.mytutoron.model.domain.Role;
import imtiaz.sktech.mytutoron.model.dto.request.CreateRoleRequest;
import imtiaz.sktech.mytutoron.model.dto.request.UpdateRoleRequest;
import imtiaz.sktech.mytutoron.persistence.entity.UserEntity;
import imtiaz.sktech.mytutoron.persistence.repository.RoleRepository;
import imtiaz.sktech.mytutoron.persistence.repository.UserRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class RoleService {
    public static final String ROLE_NOT_FOUND = "Role not found";
    private final RoleMapper roleMapper;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public Page<Role> getAll(Pageable pageable) {
        return roleRepository.findAll(pageable).map(roleMapper::toDomain);
    }

    public Role getOne(UUID id) {
        var roleEntity = roleRepository.findById(id).orElseThrow(() -> new NotFoundException(ROLE_NOT_FOUND));
        return roleMapper.toDomain(roleEntity);
    }

    public UUID createRole(CreateRoleRequest request) {
        var roleEntity = roleMapper.toEntity(request);
        roleEntity.setId(UUID.randomUUID());
        var savedEntity = roleRepository.save(roleEntity);
        return savedEntity.getId();
    }

    public void updateOne(UpdateRoleRequest request, UUID id) {
        var roleEntity = roleRepository.findById(id).orElseThrow(() -> new NotFoundException(ROLE_NOT_FOUND));
        roleEntity.setName(request.getName());
        roleEntity.setDescription(request.getDescription());
        roleRepository.save(roleEntity);
    }

    public void deleteRole(UUID id) {
    	var roleEntity = roleRepository.findById(id).orElseThrow(() -> new NotFoundException(ROLE_NOT_FOUND));
    	Set<UserEntity> users = roleEntity.getUsers();
    	for(UserEntity userEntity : users) {
    		userEntity.getRoles().remove(roleEntity);
    		userRepository.save(userEntity);
    	}
    	roleEntity.getUsers().removeAll(users);
        roleRepository.deleteById(id);
    }
}
