package com.nexora.sso.controller;
import com.nexora.config.to.ResponseDto;
import com.nexora.config.utils.IpUtils;
import com.nexora.sso.model.to.AuthenticationRequestTO;
import com.nexora.sso.model.to.AuthenticationResponseTO;
import com.nexora.sso.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = {"*"})
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<ResponseDto<AuthenticationResponseTO>> authenticate(
            @Valid @RequestBody AuthenticationRequestTO request,
            BindingResult bindingResult,
            HttpServletRequest httpRequest
    ) {
        String clientIp = IpUtils.getClientIp(httpRequest);
        request.setClientIp(clientIp);

        return ResponseEntity.ok(authenticationService.authenticate(request, bindingResult));
    }
}
