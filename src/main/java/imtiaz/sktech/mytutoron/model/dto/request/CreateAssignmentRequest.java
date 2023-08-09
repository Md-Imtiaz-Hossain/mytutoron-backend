package imtiaz.sktech.mytutoron.model.dto.request;

import lombok.Getter;
import lombok.Setter;
import imtiaz.sktech.mytutoron.model.enums.AssignmentStatus;
import imtiaz.sktech.mytutoron.persistence.entity.AssignmentCategoryEntity;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class CreateAssignmentRequest {
    private UUID id;
    @NotEmpty(message = "Please enter a Assignment Title.")
    private String assignmentTitle;
    @NotEmpty(message = "Please give additional Note.")
    private String additionalNote;
    @NotNull(message = "Select Assignment Category.")
    private AssignmentCategoryEntity AssignmentCategory;
}
