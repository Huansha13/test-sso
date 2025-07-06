package com.nexora.sso.model.to;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

import jakarta.validation.constraints.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestTO {
    @NotBlank(message = "El usuario es obligatorio")
    private String usuario;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String password;

    @NotEmpty(message = "Debe asignar al menos un rol")
    private Set<String> roles;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @Email(message = "Correo electrónico inválido")
    @NotBlank(message = "El correo es obligatorio")
    private String correo;


}
