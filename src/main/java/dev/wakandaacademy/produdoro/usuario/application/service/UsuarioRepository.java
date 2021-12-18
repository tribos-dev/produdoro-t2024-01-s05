package dev.wakandaacademy.produdoro.usuario.application.service;

import dev.wakandaacademy.produdoro.usuario.domain.Usuario;

public interface UsuarioRepository {
	Usuario salva(Usuario usuario);
}
