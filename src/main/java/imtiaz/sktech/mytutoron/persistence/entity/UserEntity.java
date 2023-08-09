package imtiaz.sktech.mytutoron.persistence.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import imtiaz.sktech.mytutoron.constant.EntityConstant;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = EntityConstant.USER)
public class UserEntity extends BaseEntity {
	private String firstName;
	private String lastName;
	private String email;
	@JsonIgnore
	private String password;
	private Boolean isEnabled = true;
	private Boolean isVerified = false;
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "users_roles",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id")
	)
	private Set<RoleEntity> roles = new HashSet<>();

}
