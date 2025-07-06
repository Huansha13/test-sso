package com.nexora.sso.service;

import com.nexora.config.to.ResponseDto;
import com.nexora.sso.model.to.AuthenticationRequestTO;
import com.nexora.sso.model.to.AuthenticationResponseTO;
import org.springframework.validation.BindingResult;


public interface AuthService {
    ResponseDto<AuthenticationResponseTO> authenticate(AuthenticationRequestTO request, BindingResult bindingResult);
}
