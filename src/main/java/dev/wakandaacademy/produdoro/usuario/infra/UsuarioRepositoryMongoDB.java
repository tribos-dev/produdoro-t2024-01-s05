package dev.wakandaacademy.produdoro.usuario.infra;

import dev.wakandaacademy.produdoro.handler.APIException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import dev.wakandaacademy.produdoro.usuario.application.repository.UsuarioRepository;
import dev.wakandaacademy.produdoro.usuario.domain.Usuario;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
@Log4j2
public class UsuarioRepositoryMongoDB implements UsuarioRepository {
	private final UsuarioMongoSpringRepository usuarioMongoRepository;

	@Override
	public Usuario salva(Usuario usuario) {
		return usuarioMongoRepository.save(usuario);
	}

	@Override
	public Usuario buscaUsuarioPorId(UUID idUsuario) {
		log.info("[inicia] UsuarioMongoSpringRepository - buscaUsuarioPorId");
		Usuario usuario = usuarioMongoRepository.findByIdUsuario(idUsuario)
				.orElseThrow(() -> APIException.build(HttpStatus.BAD_REQUEST, "Usuario não encontrado!"));
		log.info("[finaliza] UsuarioMongoSpringRepository - buscaUsuarioPorId");
		return usuario;
	}

	@Override
	public Usuario buscaUsuarioPorEmail(String emailUsuario) {
		log.info("[inicia] UsuarioMongoSpringRepository - buscaUsuarioPorEmail");
		Usuario usuario = usuarioMongoRepository.findByEmail(emailUsuario)
				.orElseThrow(() -> APIException.build(HttpStatus.BAD_REQUEST, "Usuario não encontrado!"));
		log.info("[finaliza] UsuarioMongoSpringRepository - buscaUsuarioPorEmail");
		return usuario;
	}
}
