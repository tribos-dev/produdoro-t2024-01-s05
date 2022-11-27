package dev.wakandaacademy.produdoro.usuario.infra;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;

import dev.wakandaacademy.produdoro.usuario.domain.Usuario;

public interface UsuarioMongoSpringRepository extends MongoRepository<Usuario, UUID> {
    Optional<Usuario> findByIdUsuario(UUID idUsuario);
    Optional<Usuario> findByEmail(String email);
}
