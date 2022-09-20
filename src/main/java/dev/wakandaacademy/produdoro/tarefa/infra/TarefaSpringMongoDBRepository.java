package dev.wakandaacademy.produdoro.tarefa.infra;

import dev.wakandaacademy.produdoro.tarefa.domain.Tarefa;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface TarefaSpringMongoDBRepository extends MongoRepository<Tarefa, UUID> {
}
