package com.self.e_commerce.Repository;

import com.self.e_commerce.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface RoleInfoRepo extends JpaRepository<Role, Integer> {

    Optional<Role> findByName(String roleUser);
}
