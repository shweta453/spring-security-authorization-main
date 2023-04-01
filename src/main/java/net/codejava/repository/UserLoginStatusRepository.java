package net.codejava.repository;

import net.codejava.entity.UserLoginStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLoginStatusRepository extends JpaRepository<UserLoginStatus,Integer> {

    UserLoginStatus findByUsername(String username);

    boolean existsByUsername(String username);
}
