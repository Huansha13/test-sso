package com.nexora.config.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "sub_menus")
@Getter
@Setter
public class SubMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String codigo;

    private String nombre;
    private String ruta;

    @JsonIgnore
    private String accesoPara;
}
