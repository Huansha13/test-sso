package com.nexora.sso.repository;

import com.nexora.sso.model.user.UsuarioInterno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<UsuarioInterno, Long> {

    Optional<UsuarioInterno> findByUsuario(String usuario);
}
