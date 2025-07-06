package com.nexora.sso.repository;

import com.nexora.sso.model.user.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, String> {
    Optional<Rol> findByName(String name);
}
