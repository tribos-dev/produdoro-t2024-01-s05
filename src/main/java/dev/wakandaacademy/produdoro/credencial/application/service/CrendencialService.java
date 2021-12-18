package dev.wakandaacademy.produdoro.credencial.application.service;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import dev.wakandaacademy.produdoro.usuario.application.api.UsuarioNovoRequest;

@Service
public class CrendencialService implements CredencialApplicationService {

	@Override
	public void criaNovaCredencial(@Valid UsuarioNovoRequest usuarioNovo) {
		//TODO Implementar metodo para criar nova creencial
	}

}
