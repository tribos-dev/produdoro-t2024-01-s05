package dev.wakandaacademy.produdoro.tarefa.infra;

import dev.wakandaacademy.produdoro.tarefa.domain.StatusTarefa;
import dev.wakandaacademy.produdoro.tarefa.domain.Tarefa;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TarefaSpringMongoDBRepository extends MongoRepository<Tarefa, UUID> {
    Optional<Tarefa> findByIdTarefa(UUID idTarefa);

<<<<<<< HEAD
    Integer countByIdUsuario(UUID idUsuario);
=======
    long deleteByIdUsuarioAndStatus(UUID idUsuario, StatusTarefa concluida);

    @Query("{'idUsuario': ?0, 'statusAtivacao': 'ATIVA'}")
    List<Tarefa> findTarefasAtivasByUsuario(UUID idUsuario);

    List<Tarefa> findAllByIdUsuario(UUID idUsuario);
>>>>>>> 6eb8e3958c1c6eb3111e7b77631aa903b2185ff6
}
