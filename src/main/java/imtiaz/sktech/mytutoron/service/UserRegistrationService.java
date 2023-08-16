package imtiaz.sktech.mytutoron.service;

import imtiaz.sktech.mytutoron.constant.AppUtils;
import imtiaz.sktech.mytutoron.exception.custom.AlreadyExistsException;
import imtiaz.sktech.mytutoron.exception.custom.NotFoundException;
import imtiaz.sktech.mytutoron.mapper.UserMapper;
import imtiaz.sktech.mytutoron.model.domain.User;
import imtiaz.sktech.mytutoron.model.dto.request.CreateUserRequest;
import imtiaz.sktech.mytutoron.model.dto.request.UpdateUserRequest;
import imtiaz.sktech.mytutoron.model.pagination.PaginationArgs;
import imtiaz.sktech.mytutoron.persistence.entity.RoleEntity;
import imtiaz.sktech.mytutoron.persistence.entity.UserEntity;
import imtiaz.sktech.mytutoron.persistence.repository.RoleRepository;
import imtiaz.sktech.mytutoron.persistence.repository.UserRepository;
import imtiaz.sktech.mytutoron.persistence.specification.UserSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.*;

@RequiredArgsConstructor
@Service
public class UserRegistrationService {
    private static final String USER_NOT_FOUND = "User not found";
    private static final String USER_SELLER = "User_Seller";
    private static final String ROLE_NOT_FOUND = "Role not found";
    private static final String USER_ALREADY_EXISTS = "User already exists with email: ";
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    private Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    private Boolean existsByEmailAndIdNot(String email, UUID userId) {
        return userRepository.existsByEmailAndIdNot(email, userId);
    }

    private User saveUser(UserEntity userEntity) {
        var savedUserEntity = userRepository.save(userEntity);
        return userMapper.toDomain(savedUserEntity);
    }

    public User userRegistration(CreateUserRequest request) {

        var userEntity = userMapper.toEntity(request);
        UUID userId = UUID.randomUUID();
        userEntity.setId(userId);

        if (existsByEmail(request.getEmail())) {
            throw new AlreadyExistsException(USER_ALREADY_EXISTS + request.getEmail());
        }

        Set<RoleEntity> roles = new HashSet<>();
        RoleEntity emptyRole = roleRepository.findByName(USER_SELLER)
                .orElseThrow(() -> new NotFoundException("No Role found! Please select a role and submit again."));
        roles.add(emptyRole);
        request.getRoleIds().forEach((roleId) -> {
            RoleEntity roleEntity = roleRepository.findById(roleId).orElseThrow(() -> new NotFoundException(ROLE_NOT_FOUND));
            roles.add(roleEntity);
        });

        userEntity.setRoles(roles);
        String encodedPassword = encodePasswordUsingString(request.getPassword());
        userEntity.setPassword(encodedPassword);
        return saveUser(userEntity);
    }

    public User updateUser(UpdateUserRequest request) {
        var userEntity = userRepository.findById(request.getId()).orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        if (existsByEmailAndIdNot(request.getEmail(), request.getId())) {
            throw new AlreadyExistsException(USER_ALREADY_EXISTS + request.getEmail());
        }

        if (request.getRoleIds().isEmpty()) {
            throw new NotFoundException("No Role found! Please select a role and submit again.");
        }

        Set<RoleEntity> roles = new HashSet<>();
        request.getRoleIds().forEach((roleId) -> {
            RoleEntity roleEntity = roleRepository.findById(roleId).orElseThrow(() -> new NotFoundException(ROLE_NOT_FOUND));
            roles.add(roleEntity);
        });

        userEntity.setFirstName(request.getFirstName());
        userEntity.setLastName(request.getLastName());
        userEntity.setEmail(request.getEmail());
        userEntity.setRoles(roles);
        return saveUser(userEntity);
    }

    public void updateUserPassword(String userName, String password) {
        var userEntity = userRepository.findByEmail(userName).orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        userEntity.setPassword(password);
        userRepository.save(userEntity);
    }

    public void deleteUser(UUID userId) {
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        userRepository.delete(userEntity);
    }

    public String encodePasswordUsingString(String password) {
        return passwordEncoder.encode(password);
    }

}
