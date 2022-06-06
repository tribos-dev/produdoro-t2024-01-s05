package dev.wakandaacademy.produdoro.credencial.application.service;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import dev.wakandaacademy.produdoro.credencial.application.repository.CredencialRepository;
import dev.wakandaacademy.produdoro.credencial.domain.Credencial;
import dev.wakandaacademy.produdoro.usuario.application.api.UsuarioNovoRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CrendencialService implements CredencialApplicationService {
	private final CredencialRepository credencialRepository;
	
	@Override
	public void criaNovaCredencial(@Valid UsuarioNovoRequest usuarioNovo) {
		log.info("[start] CrendencialService - criaNovaCredencial");
		var novaCredencial = new Credencial(usuarioNovo.getSenha(), usuarioNovo.getSenha());
		credencialRepository.salva(novaCredencial);
		log.info("[finish] CrendencialService - criaNovaCredencial");
	}

}
