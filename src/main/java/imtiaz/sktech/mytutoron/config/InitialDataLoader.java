//package imtiaz.sktech.starter.config;
//
//import lombok.RequiredArgsConstructor;
//import imtiaz.sktech.starter.constant.AppConstant;
//import imtiaz.sktech.starter.persistence.entity.RoleEntity;
//import imtiaz.sktech.starter.persistence.entity.UserEntity;
//import imtiaz.sktech.starter.persistence.repository.RoleRepository;
//import imtiaz.sktech.starter.persistence.repository.UserRepository;
//import org.springframework.context.ApplicationListener;
//import org.springframework.context.event.ApplicationContextEvent;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//import java.util.HashSet;
//import java.util.Set;
//
//@RequiredArgsConstructor
//@Component
//public class InitialDataLoader implements ApplicationListener<ApplicationContextEvent> {
//
//    private final UserRepository userRepository;
//
//    private final RoleRepository roleRepository;
//
//    private final PasswordEncoder passwordEncoder;
//
//    @Override
//    public void onApplicationEvent(ApplicationContextEvent event) {
//        String superAdminFirst = AppConstant.SUPER_ADMIN_FIRST_NAME;
//        String superAdminLastName = AppConstant.SUPER_ADMIN_LAST_NAME;
//        String superAdminEmail = AppConstant.SUPER_ADMIN_EMAIL;
//        String superAdminPassword = AppConstant.SUPER_ADMIN_PASSWORD;
//        String superAdminRoleName = AppConstant.SUPER_ADMIN_ROLE_NAME;
//        String superAdminRoleDescription = AppConstant.SUPER_ADMIN_ROLE_DESCRIPTION;
//        String consumerRoleName = AppConstant.CONSUMER_ROLE_NAME;
//        String consumerRoleDescription = AppConstant.CONSUMER_ROLE_DESCRIPTION;
//
//        if (!roleRepository.existsByName(superAdminRoleName)) {
//            RoleEntity superAdminRole = new RoleEntity();
//            superAdminRole.setName(superAdminRoleName);
//            superAdminRole.setDescription(superAdminRoleDescription);
//
//            roleRepository.save(superAdminRole);
//        }
//
//        if (!roleRepository.existsByName(consumerRoleName)) {
//            RoleEntity consumerRole = new RoleEntity();
//            consumerRole.setName(consumerRoleName);
//            consumerRole.setDescription(consumerRoleDescription);
//
//            roleRepository.save(consumerRole);
//        }
//
//        if (userRepository.existsByEmail(superAdminEmail)) {
//            return;
//        }
//
//        Set<RoleEntity> roles = new HashSet<>();
//        RoleEntity role = roleRepository.findByName(superAdminRoleName).orElse(null);
//        if (role != null) {
//            roles.add(role);
//        }
//
//        UserEntity user = new UserEntity();
//        user.setFirstName(superAdminFirst);
//        user.setLastName(superAdminLastName);
//        user.setEmail(superAdminEmail);
//        user.setPassword(passwordEncoder.encode(superAdminPassword));
//        user.setIsEnabled(true);
//        user.setIsVerified(true);
//        user.setRoles(roles);
//
//        userRepository.save(user);
//
//    }
//}
