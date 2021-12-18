package dev.wakandaacademy.produdoro.usuario.infra;

import org.springframework.stereotype.Repository;

import dev.wakandaacademy.produdoro.usuario.application.service.UsuarioRepository;
import dev.wakandaacademy.produdoro.usuario.domain.Usuario;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UsuarioRepositoryMongoDB implements UsuarioRepository {
	private final UsuarioMongoSpringRepository usuarioMongoRepository;

	@Override
	public Usuario salva(Usuario usuario) {
		return usuarioMongoRepository.save(usuario);
	}
}
