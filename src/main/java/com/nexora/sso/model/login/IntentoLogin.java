package com.nexora.sso.model.login;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity(name = "intentos_login")
@Getter
@Setter
public class IntentoLogin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String usuario;

    @Column(nullable = false, length = 45)
    private String ip;

    @Column(nullable = false)
    private int intentos;

    private LocalDateTime bloqueadoHasta;

    private LocalDateTime ultimaFallo;
}
