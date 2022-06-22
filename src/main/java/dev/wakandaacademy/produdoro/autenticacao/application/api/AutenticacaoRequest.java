package dev.wakandaacademy.produdoro.autenticacao.application.api;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AutenticacaoRequest {
	@NotNull
	@NotBlank(message = "O usuário não pode estar em branco!")
	@Email
	private String usuario;
	@Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
	@NotNull
	private String senha;

	public UsernamePasswordAuthenticationToken getUserPassToken() {
		return new UsernamePasswordAuthenticationToken(usuario, senha);
	}
}
