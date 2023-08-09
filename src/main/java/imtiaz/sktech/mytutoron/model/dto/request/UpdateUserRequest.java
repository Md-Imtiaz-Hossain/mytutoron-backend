package imtiaz.sktech.mytutoron.model.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class UpdateUserRequest {

	@NotNull(message = "Id is required.")
	private UUID id;

	@NotEmpty(message = "First name is required.")
	@Size(min = 2, max = 20, message = "First name must be between 2 to 20 characters.")
	private String firstName;
	
	@NotEmpty(message = "Last name is required.")
	@Size(min = 2, max = 20, message = "Last name must be between 2 to 20 characters.")
	private String lastName;
	
	@NotEmpty(message = "Email is required.")
	@Email(message = "Enter a valid email address.")
	private String email;

	private Set<UUID> roleIds = new HashSet<>();
}
