package dev.wakandaacademy.produdoro.usuario.application.service;

import javax.validation.Valid;

import dev.wakandaacademy.produdoro.usuario.application.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import dev.wakandaacademy.produdoro.credencial.application.service.CredencialApplicationService;
import dev.wakandaacademy.produdoro.pomodoro.application.service.PomodoroApplicationService;
import dev.wakandaacademy.produdoro.usuario.application.api.UsuarioCriadoResponse;
import dev.wakandaacademy.produdoro.usuario.application.api.UsuarioNovoRequest;
import dev.wakandaacademy.produdoro.usuario.domain.Usuario;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.UUID;

@Service
@Log4j2
@RequiredArgsConstructor
public class UsuarioService implements UsuarioApplicationService {
	private final PomodoroApplicationService pomodoroService;
	private final CredencialApplicationService credencialService;
	private final UsuarioRepository usuarioRepository;

	@Override
	public UsuarioCriadoResponse criaNovoUsuario(@Valid UsuarioNovoRequest usuarioNovo) {
		log.info("[start] UsuarioService - criaNovoUsuario");
		var configuracaoPadrao = pomodoroService.getConfiguracaoPadrao();
		credencialService.criaNovaCredencial(usuarioNovo);
		var usuario = new Usuario(usuarioNovo,configuracaoPadrao);
		usuarioRepository.salva(usuario);
		log.info("[finish] UsuarioService - criaNovoUsuario");
		return new UsuarioCriadoResponse(usuario);
	}


	@Override
	public UsuarioCriadoResponse buscaUsuarioPorId(UUID idUsuario) {
		log.info("[inicia] UsuarioApplicationService - buscaUsuarioPorId");
		Usuario usuario = usuarioRepository.buscaUsuarioPorId(idUsuario);
		log.info("[finaliza] UsuarioApplicationService - buscaUsuarioPorId");
		return new UsuarioCriadoResponse(usuario);
	}


}
