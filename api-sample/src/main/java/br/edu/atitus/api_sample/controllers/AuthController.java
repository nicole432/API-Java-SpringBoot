package br.edu.atitus.api_sample.controllers;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.atitus.api_sample.dtos.SignupDTO;
import br.edu.atitus.api_sample.entities.UserEntity;
import br.edu.atitus.api_sample.entities.UserType;
import br.edu.atitus.api_sample.services.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	private final UserService service;
	
	public AuthController() {
		super();
		this.service = new UserService();
	}

	@PostMapping("/signup")
	public ResponseEntity<UserEntity> signup(@RequestBody SignupDTO dto) throws Exception {
		//criamos a entidade
		UserEntity user = new UserEntity();
		//copia propriedades
		BeanUtils.copyProperties(dto, user);
		//seta os valores que nao vieram no DTO
		user.setType(UserType.Common);
		
		service.save(user);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(user);
	}
	
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<String> hanlderException(Exception ex) {
		String message = ex.getMessage().replaceAll("/r/n", "");
		return ResponseEntity.badRequest().body(message);
	}
}
