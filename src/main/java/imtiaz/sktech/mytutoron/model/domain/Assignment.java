package imtiaz.sktech.mytutoron.model.domain;

import imtiaz.sktech.mytutoron.persistence.entity.AssignmentCategoryEntity;
import lombok.Getter;
import lombok.Setter;
import imtiaz.sktech.mytutoron.model.enums.AssignmentStatus;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class Assignment {
    private UUID id;
    private String assignmentTitle;
    private String additionalNote;
    private AssignmentCategoryEntity assignmentCategory;
}
