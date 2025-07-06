package com.nexora.sso.service.impl;

import com.nexora.config.exceptions.IntentosLoginException;
import com.nexora.sso.model.login.IntentoLogin;
import com.nexora.sso.repository.IntentoLoginRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class IntentosLoginService {
    private static final int MAX_INTENTOS = 3;
    private static final int DAYS = 1;

    private final IntentoLoginRepository intentoLoginRepository;

    public IntentosLoginService(IntentoLoginRepository intentoLoginRepository) {
        this.intentoLoginRepository = intentoLoginRepository;
    }

    public String registrarFallo(String usuario, String ip) {
        var intentoOpt = intentoLoginRepository.findByUsuarioAndIp(usuario, ip);
        IntentoLogin intento = intentoOpt.orElseGet(() -> {
            IntentoLogin nuevo = new IntentoLogin();
            nuevo.setUsuario(usuario);
            nuevo.setIp(ip);
            nuevo.setIntentos(1);
            return nuevo;
        });

        if (intentoOpt.isPresent()) {
            intento.setIntentos(intento.getIntentos() + 1);
        }

        intento.setUltimaFallo(LocalDateTime.now());

        if (intento.getIntentos() >= MAX_INTENTOS) {
            intento.setBloqueadoHasta(LocalDateTime.now().plusDays(DAYS));
        }
        intentoLoginRepository.save(intento);


        int intentosRestantes = MAX_INTENTOS - intento.getIntentos();
        if (intentosRestantes > 0) {
            return "Datos incorrectos. Te quedan "
                    + intentosRestantes
                    + " intento(s) antes de que tu usuario sea bloqueado.";
        }

        this.checkIntentos(usuario, ip);
        return "";
    }

    @Transactional
    public void reiniciarIntentos(String usuario, String ip) {
        intentoLoginRepository.deleteByUsuarioAndIp(usuario, ip);
    }

    public void checkIntentos(String usuario, String ip) {
        var intentoOpt = intentoLoginRepository.findByUsuarioAndIp(usuario, ip);
        if (intentoOpt.isPresent()) {
            var intento = intentoOpt.get();
            if (intento.getBloqueadoHasta() != null && LocalDateTime.now().isBefore(intento.getBloqueadoHasta())) {
                // Formato legible para humanos: dd/MM/yyyy HH:mm
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                String fechaFormateada = intento.getBloqueadoHasta().format(formatter);
                throw new IntentosLoginException(
                        "Usuario bloqueado. Podrás intentar nuevamente después de: "
                                + fechaFormateada
                );
            }
        }
    }
}
