package com.nexora.sso.model.to;

import com.nexora.sso.model.user.Rol;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioTO {
    private Long id;
    private String usuario;
    private String nombre;
    private String correo;
    private Set<Rol> rol;
}
