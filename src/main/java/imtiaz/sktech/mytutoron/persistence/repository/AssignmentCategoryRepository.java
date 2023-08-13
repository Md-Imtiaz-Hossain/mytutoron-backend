package imtiaz.sktech.mytutoron.persistence.repository;

import imtiaz.sktech.mytutoron.persistence.entity.AssignmentCategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AssignmentCategoryRepository extends JpaRepository<AssignmentCategoryEntity, UUID> {

}
