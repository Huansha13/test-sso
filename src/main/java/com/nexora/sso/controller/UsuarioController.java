package com.nexora.sso.controller;

import com.nexora.config.to.ResponseDto;
import com.nexora.sso.model.to.RegisterRequestTO;
import com.nexora.sso.model.to.UsuarioTO;
import com.nexora.sso.service.SsoUserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/usuario-controller")
@CrossOrigin
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class UsuarioController {

    private final SsoUserService userService;

    @PostMapping("/register")
    public ResponseEntity<ResponseDto<Boolean>> register(
            @RequestBody RegisterRequestTO request
    ) {
        return ResponseEntity.ok(userService.register(request));
    }

    @GetMapping("/obtenerUsuarios")
    @PreAuthorize("hasAnyRole('SUPERVISOR', 'ADMIN')")
    public ResponseEntity<ResponseDto<List<UsuarioTO>>> obtenerUsuarios() {
        return ResponseEntity.ok(userService.obtenerUsuarios());
    }

    @DeleteMapping("/eliminarUsuario/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public  ResponseEntity<ResponseDto<Boolean>> eliminarUsuario(@PathVariable Long id) {
        return ResponseEntity.ok(userService.eliminarUsuario(id));
    }
}
