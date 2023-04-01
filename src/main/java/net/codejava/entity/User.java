package net.codejava.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class User {

	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userId;
	@Email
	private String email;
	private String username;
	private String password;
	private boolean isEnabled;

	@CreationTimestamp
	private Timestamp passwordCreatedDate;

	@UpdateTimestamp
	@Column(name = "LAST_MODIFIED_ON")
	private Timestamp modifiedOn;

	@Column(name = "reset_password_token")
	private String resetPasswordToken;

	private String incorrectPasswordCount;

	@Column(name = "IS_LOCKED")
	private String isLocked;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(
			name = "users_roles",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id")
			)
	private Set<Role> roles = new HashSet<>();
}
