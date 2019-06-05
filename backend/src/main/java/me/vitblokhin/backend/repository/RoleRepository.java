package me.vitblokhin.backend.repository;

import me.vitblokhin.backend.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
} // interface RoleRepository
