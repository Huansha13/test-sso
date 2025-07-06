package com.nexora.sso.service.impl;

import com.nexora.config.entity.Menu;
import com.nexora.config.entity.SubMenu;
import com.nexora.config.enums.MensajeEnum;
import com.nexora.config.enums.SummaryEnum;
import com.nexora.config.exceptions.FieldValidationException;
import com.nexora.config.exceptions.IntentosLoginException;
import com.nexora.config.repository.MenuRepository;
import com.nexora.config.to.ResponseDto;
import com.nexora.sso.config.JwtService;
import com.nexora.sso.model.to.AuthenticationRequestTO;
import com.nexora.sso.model.to.AuthenticationResponseTO;
import com.nexora.sso.model.user.Rol;
import com.nexora.sso.model.user.UsuarioInterno;
import com.nexora.sso.service.AuthService;
import com.nexora.sso.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImp implements AuthService {

    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final IntentosLoginService  intentosLoginService;
    private final MenuRepository menuRepository;

    @Override
    public ResponseDto<AuthenticationResponseTO> authenticate(AuthenticationRequestTO request, BindingResult bindingResult) {
        ResponseDto<AuthenticationResponseTO> response = new ResponseDto<>(SummaryEnum.POST_LOGIN.getValue());

        // Validación de campos del request
        if (bindingResult.hasErrors()) {
            response.setMessage(MensajeEnum.CAMPO_INCORRECTO.getValue());
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return response;
        }

        String usuario = (request.getUsuario() != null) ? request.getUsuario().trim().toUpperCase() : "";
        String password = (request.getPassword() != null) ? request.getPassword() : "";

        try {
            // Validación simple de formato (pero sin exponer detalles)
            if (usuario.length() < 3 || usuario.length() > 10) {
                throw new FieldValidationException(MensajeEnum.CAMPO_USUARIO_INCORRECTO.getValue());
            }

            if (password.length() < 8 || password.length() > 16) {
                throw new FieldValidationException(MensajeEnum.CAMPO_PASSWORD_INCORRECTO.getValue());
            }

            // Limitar intentos fallidos por usuario/IP
            intentosLoginService.checkIntentos(usuario, request.getClientIp());

            // Buscar usuario en BD antes de autenticar
            var userOpt = usuarioRepository.findByUsuario(usuario);
            if (userOpt.isEmpty()) {
                String mss = intentosLoginService.registrarFallo(usuario, request.getClientIp());
                response.setMessage(mss);
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                return response;
            }
            var user = userOpt.get();

            // Autenticación Spring (verifica password)
            procesarAutenticacionUsuario(request, usuario, password);

            // Generar Token
            List<String> authorities = user.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .toList();

            List<String> codRoles = user.getRoles()
                    .stream()
                    .map(Rol::getCode)
                    .toList();

            var jwtToken = jwtService.generateToken(user.getUsuario(), authorities);

            var listaMenu = obtenerListaMenuByUser(user);

            // Response
            response.setMessage(MensajeEnum.LOGIN_OK.getValue());
            response.setValue(AuthenticationResponseTO.builder()
                    .token("Bearer " + jwtToken)
                    .usuario(user.getUsuario())
                    .nombre(user.getNombre())
                    .correo(user.getCorreo())
                    .roles(codRoles)
                    .menus(listaMenu)
                    .build()
            );

            intentosLoginService.reiniciarIntentos(usuario, request.getClientIp());

        } catch (FieldValidationException ex) {
            response.setMessage(ex.getMessage());
            response.setStatus(HttpStatus.BAD_REQUEST.value());
        } catch (IntentosLoginException e) {
            response.setMessage(e.getMessage());
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        } catch (Exception e) {
            log.error("Error al iniciar sesión: {}", e.getMessage());
            response.setMessage("Error interno al procesar la solicitud.");
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return response;
    }

    private List<Menu> obtenerListaMenuByUser(UsuarioInterno user) {
        // Obtener los códigos de los roles del usuario en un Set para búsquedas rápidas
        Set<String> userRoleCodes = user.getRoles().stream()
                .map(Rol::getCode)
                .collect(Collectors.toSet());

        return userRoleCodes.stream()
                .flatMap(roleCode -> menuRepository.findByAccesoParaContainingOrderByCodigo(roleCode)
                        .orElseGet(Collections::emptyList)
                        .stream())
                .distinct()
                .peek(menu -> {
                    // Filtra y ordena los submenús según el acceso de los roles del usuario
                    List<SubMenu> filteredAndSortedSubMenus = menu.getSubMenus().stream()
                            .filter(subMenu -> Arrays.stream(subMenu.getAccesoPara().split(","))
                                    .anyMatch(userRoleCodes::contains))
                            .sorted(Comparator.comparingLong(SubMenu::getId))
                            .toList();
                    menu.setSubMenus(filteredAndSortedSubMenus);
                })
                .toList();
    }




    private void procesarAutenticacionUsuario(
            AuthenticationRequestTO request,
            String usuario,
            String password
    ) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(usuario, password)
            );
        } catch (AuthenticationException ex) {
            String mss = intentosLoginService.registrarFallo(usuario, request.getClientIp());
            throw  new IntentosLoginException(mss);
        }
    }
}
