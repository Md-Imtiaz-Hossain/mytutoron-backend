package imtiaz.sktech.mytutoron.model.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class AssignmentCategory {
    private UUID id;
    private String name;
    private String description;
}
