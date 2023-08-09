package imtiaz.sktech.mytutoron.persistence.entity;

import lombok.Getter;
import lombok.Setter;
import imtiaz.sktech.mytutoron.constant.EntityConstant;
import imtiaz.sktech.mytutoron.model.enums.AssignmentStatus;

import javax.persistence.*;
import java.math.BigDecimal;

@Setter
@Getter
@Entity
@Table(name = EntityConstant.ASSIGNMENT)
public class AssignmentEntity extends BaseEntity {
    private String assignmentTitle;
    private String additionalNote;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Assignment_category_id")
    private AssignmentCategoryEntity assignmentCategory;
}
