package net.codejava.repository;

import net.codejava.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface RoleRepository extends JpaRepository<Role,Integer> {


    Role findByName(String name);
}
