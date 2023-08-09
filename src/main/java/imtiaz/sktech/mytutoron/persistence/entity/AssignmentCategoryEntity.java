package imtiaz.sktech.mytutoron.persistence.entity;

import lombok.Getter;
import lombok.Setter;
import imtiaz.sktech.mytutoron.constant.EntityConstant;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = EntityConstant.ASSIGNMENT_CATEGORY)
public class AssignmentCategoryEntity extends BaseEntity{
    @Column(length = 100)
    private String name;

    @Column(length = 1000)
    private String description;

    @OneToMany(mappedBy = "assignmentCategory", cascade = CascadeType.ALL)
    private List<AssignmentEntity> assignments;
}
