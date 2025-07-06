package com.nexora.sso.model.to;

import com.nexora.config.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponseTO {
    private String token;
    private String usuario;
    private String nombre;
    private String correo;
    private List<String> roles;
    private List<Menu> menus;

}
