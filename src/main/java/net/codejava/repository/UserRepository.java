package net.codejava.repository;

import net.codejava.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	@Query("SELECT u FROM User u WHERE u.username = :username")
	public User getUserByUsername(@Param("username") String username);
    User findByUsername(String username);

	@Query("SELECT u FROM User u WHERE u.email = ?1")
	public User findByEmail(String email);

	Optional<User> findByUserId(int userId);

	User findByResetPasswordToken(String token);

	boolean existsByUsername(String username);

	List<User> findAllByUsernameIn(List<String> userNames);
}
