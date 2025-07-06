package com.nexora;

import com.nexora.config.to.ResponseDto;
import com.nexora.sso.model.to.RegisterRequestTO;
import com.nexora.sso.model.user.Rol;
import com.nexora.sso.service.SsoUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class SsoApplication  implements CommandLineRunner
{

	@Autowired
	private SsoUserService userService;

	public static void main(String[] args) {
		SpringApplication.run(SsoApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		Set<String> roles = new HashSet<>();
		roles.add("ADMIN");

		RegisterRequestTO registerRequest = new RegisterRequestTO();
		registerRequest.setUsuario("jkin13");
		registerRequest.setPassword("12345678h");
		registerRequest.setNombre("Huansha Leyva Yefer");
		registerRequest.setRoles(roles);
		registerRequest.setCorreo("");

		//ResponseDto<Boolean> status = userService.register(registerRequest);
		//System.out.println("Registro exitoso? " + status.getStatus());
	}

}
