package imtiaz.sktech.mytutoron.service;

import lombok.RequiredArgsConstructor;
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
public class UserService {
    private static final String USER_NOT_FOUND = "User not found";
    private static final String ROLE_NOT_FOUND = "Role not found";
    private static final String USER_ALREADY_EXISTS = "User already exists with email: ";
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userRepository.findAll().stream().map(userMapper::toDomain).toList();
    }

    public Page<User> getAllPaginatedUsers(PaginationArgs paginationArgs) {
        Pageable pageable = AppUtils.getPageable(paginationArgs);

        Page<UserEntity> userEntityPage;
        Map<String, Object> specificParameters = AppUtils.getSpecificParameters(paginationArgs.getParameters());
        if (!specificParameters.isEmpty()) {
            Specification<UserEntity> userSpecification = UserSpecification.getSpecification(specificParameters);
            userEntityPage = userRepository.findAll(userSpecification, pageable);
        } else {
            userEntityPage = userRepository.findAll(pageable);
        }

        List<User> users = userEntityPage.stream().map(userMapper::toDomain).toList();
        return new PageImpl<>(users, pageable, userEntityPage.getTotalElements());
    }

    public Page<User> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(userMapper::toDomain);
    }

    public User getUserById(UUID userId) {
        var userEntity = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        return userMapper.toDomain(userEntity);
    }

    public User getUserByUsername(String username) {
        var userEntity = userRepository.findByEmail(username).orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        return userMapper.toDomain(userEntity);
    }

    public User getLoggedInUser(Principal principal) {
        if (principal == null || principal.getName() == null || principal.getName().isEmpty()) {
            return createGuestUser();
        }
        String username = principal.getName();
        return getUserByUsername(username);
    }

    private User createGuestUser() {
        User guestUser = new User();
        guestUser.setFirstName("Guest");
        guestUser.setLastName("User");
        return guestUser;
    }

    public UUID getLoggedInUserId(Principal principal) {
        String username = principal.getName();
        User user = getUserByUsername(username);
        return user.getId();
    }

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

    public User createUser(CreateUserRequest request) {

        var userEntity = userMapper.toEntity(request);
        UUID userId = UUID.randomUUID();
        userEntity.setId(userId);

        if (existsByEmail(request.getEmail())) {
            throw new AlreadyExistsException(USER_ALREADY_EXISTS + request.getEmail());
        }

        Set<RoleEntity> roles = new HashSet<>();
        if (request.getRoleIds().isEmpty()) {
            RoleEntity emptyRole = roleRepository.findByName("empty_role")
                    .orElseThrow(() -> new NotFoundException("No Role found! Please select a role and submit again."));
            roles.add(emptyRole);
        } else {
            request.getRoleIds().forEach((roleId) -> {
                RoleEntity roleEntity = roleRepository.findById(roleId)
                        .orElseThrow(() -> new NotFoundException(ROLE_NOT_FOUND));
                roles.add(roleEntity);
            });
        }

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

    public long countTotalUser() {
        return userRepository.count();
    }
}
