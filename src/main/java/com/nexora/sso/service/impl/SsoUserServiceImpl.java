package com.nexora.sso.service.impl;

import com.nexora.config.enums.MensajeEnum;
import com.nexora.config.enums.SummaryEnum;
import com.nexora.config.to.ResponseDto;
import com.nexora.sso.model.to.RegisterRequestTO;
import com.nexora.sso.model.to.UsuarioTO;
import com.nexora.sso.model.user.Rol;
import com.nexora.sso.model.user.UsuarioInterno;
import com.nexora.sso.repository.RolRepository;
import com.nexora.sso.repository.UsuarioRepository;
import com.nexora.sso.service.SsoUserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SsoUserServiceImpl implements SsoUserService {
    private final PasswordEncoder passwordEncoder;
    private final RolRepository rolRepository;
    private final UsuarioRepository usuarioRepository;


    @Transactional
    @Override
    public ResponseDto<Boolean> register(RegisterRequestTO request) {
        ResponseDto<Boolean> response = new ResponseDto<>(SummaryEnum.REGISTRO_USER.getValue());
        try {
            Set<Rol> roles = request.getRoles().stream()
                    .map(roleName -> rolRepository.findByName(roleName)
                            .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado: " + roleName)))
                    .collect(Collectors.toSet());

            var user = UsuarioInterno.builder()
                    .usuario(request.getUsuario().toUpperCase())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .roles(roles)
                    .nombre(request.getNombre())
                    .correo(request.getCorreo())
                    .build();
            usuarioRepository.save(user);
            response.setMessage(MensajeEnum.REGISTRO_OK.getValue());
        } catch (Exception e) {
            response.setException(e);
            log.error("Error al registrar usuario:", e);
        }

        return response;
    }


    @Override
    public ResponseDto<List<UsuarioTO>> obtenerUsuarios() {
        ResponseDto<List<UsuarioTO>> response = new ResponseDto<>(SummaryEnum.GET_USUARIOS.getValue());
        try {
            var usuariosBd = usuarioRepository.findAll();
            if (usuariosBd.isEmpty()) {
                response.setMessage(MensajeEnum.SIN_REGISTROS.getValue());
                response.setStatus(HttpStatus.NOT_FOUND.value());
                return response;
            }

            List<UsuarioTO> newListUsuarios = usuariosBd.stream().map(user -> {
                        UsuarioTO usuarioTO = new UsuarioTO();
                        usuarioTO.setId(user.getId());
                        usuarioTO.setUsuario(user.getUsuario());
                        usuarioTO.setNombre(user.getNombre());
                        usuarioTO.setCorreo(user.getCorreo());
                        usuarioTO.setRol(user.getRoles());
                        return usuarioTO;
                    }
            ).toList();

            response.setValue(newListUsuarios);
        } catch (Exception e) {
            response.setException(e);
            log.error("Error al obtener usuarios:", e);
        }
        return response;
    }

    @Override
    @Transactional
    public ResponseDto<Boolean> eliminarUsuario(Long id) {
        ResponseDto<Boolean> response = new ResponseDto<>(SummaryEnum.ELIMINAR_USUARIO.getValue());
        try {

            if (!usuarioRepository.existsById(id)) {
                response.setStatus(HttpStatus.NOT_MODIFIED.value());
                response.setMessage(MensajeEnum.SOLICITUD_WARN.getValue());
                return response;
            }

            usuarioRepository.deleteById(id);
            response.setValue(Boolean.TRUE);
            response.setMessage(MensajeEnum.SOLICITUD_OK.getValue());
        } catch (Exception e) {
            response.setException(e);
            log.error("Error al eliminar usuario:", e);
        }
        return response;
    }
}
