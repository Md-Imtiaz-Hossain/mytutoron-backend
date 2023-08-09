package imtiaz.sktech.mytutoron.persistence.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import imtiaz.sktech.mytutoron.persistence.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID>, JpaSpecificationExecutor<UserEntity> {
    Optional<UserEntity> findByEmail(String email);

    Boolean existsByEmail(String email);

    Boolean existsByEmailAndIdNot(String email, UUID userId);
}
