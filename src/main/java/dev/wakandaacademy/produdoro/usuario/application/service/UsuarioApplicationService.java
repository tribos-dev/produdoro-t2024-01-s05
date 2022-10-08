package dev.wakandaacademy.produdoro.usuario.application.service;

import javax.validation.Valid;

import dev.wakandaacademy.produdoro.usuario.application.api.UsuarioCriadoResponse;
import dev.wakandaacademy.produdoro.usuario.application.api.UsuarioNovoRequest;
import dev.wakandaacademy.produdoro.usuario.domain.Usuario;

import java.util.UUID;

public interface UsuarioApplicationService {
	UsuarioCriadoResponse criaNovoUsuario(UsuarioNovoRequest usuarioNovo);
    UsuarioCriadoResponse buscaUsuarioPorId(UUID idUsuario);

    Usuario buscaUsuarioAtravesId(UUID fromString);

    UsuarioCriadoResponse buscaUsuarioAtravesPorId(UUID idUsuario);
}
