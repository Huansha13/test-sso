package com.nexora.config.repository;

import com.nexora.config.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    Optional<List<Menu>> findByAccesoParaContainingOrderByCodigo(String rol);

}
