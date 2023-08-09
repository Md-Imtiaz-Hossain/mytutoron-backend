package imtiaz.sktech.mytutoron.model.domain;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
	private UUID id;
	private String firstName;
	private String lastName;
	private String email;
	private Boolean isEnabled;
	private Boolean isVerified;
	private Set<Role> roles = new HashSet<>();
}
