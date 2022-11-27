package dev.wakandaacademy.produdoro.usuario.application.repository;

import dev.wakandaacademy.produdoro.usuario.domain.Usuario;

import java.util.UUID;

public interface UsuarioRepository {
	Usuario salva(Usuario usuario);
	Usuario buscaUsuarioPorId(UUID idUsuario);
	Usuario buscaUsuarioPorEmail(String emailUsuario);
}
