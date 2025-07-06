package com.nexora.sso.repository;

import com.nexora.sso.model.login.IntentoLogin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IntentoLoginRepository extends JpaRepository<IntentoLogin, Long> {
    Optional<IntentoLogin> findByUsuarioAndIp(String usuario, String ip);
    void deleteByUsuarioAndIp(String usuario, String ip);
}
