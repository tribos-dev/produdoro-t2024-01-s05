package dev.wakandaacademy.produdoro.credencial.infra;

import org.springframework.data.mongodb.repository.MongoRepository;

import dev.wakandaacademy.produdoro.credencial.domain.Credencial;

public interface CredencialMongoSpringRepository extends MongoRepository<Credencial, String> {

}
