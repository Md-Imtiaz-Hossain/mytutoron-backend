package imtiaz.sktech.mytutoron.model.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Setter
@Getter
public class UpdateAssignmentCategoryRequest {

    @NotEmpty(message = "Please enter a Category name.")
    @Size(min = 4, max = 50, message = "Category name must be between 4 to 50 characters in length.")
    private String name;
    
    @NotEmpty(message = "Description is required.")
    @Size(min = 10, max = 1000, message = "Category description must be between 10 to 1000 characters in length.")
    private String description;

}
