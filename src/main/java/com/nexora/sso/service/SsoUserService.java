package com.nexora.sso.service;

import com.nexora.config.to.ResponseDto;
import com.nexora.sso.model.to.RegisterRequestTO;
import com.nexora.sso.model.to.UsuarioTO;

import java.util.List;

public interface SsoUserService {
    ResponseDto<Boolean> register(RegisterRequestTO request);
    ResponseDto<List<UsuarioTO>> obtenerUsuarios();
    ResponseDto<Boolean> eliminarUsuario(Long id);
}
