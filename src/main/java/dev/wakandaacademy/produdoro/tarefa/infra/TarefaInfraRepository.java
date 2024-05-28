package dev.wakandaacademy.produdoro.tarefa.infra;

import dev.wakandaacademy.produdoro.handler.APIException;
import dev.wakandaacademy.produdoro.tarefa.application.repository.TarefaRepository;
import dev.wakandaacademy.produdoro.tarefa.domain.StatusTarefa;
import dev.wakandaacademy.produdoro.tarefa.domain.Tarefa;
import dev.wakandaacademy.produdoro.usuario.domain.StatusUsuario;
import dev.wakandaacademy.produdoro.usuario.domain.Usuario;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Log4j2
@RequiredArgsConstructor
public class TarefaInfraRepository implements TarefaRepository {

    private final TarefaSpringMongoDBRepository tarefaSpringMongoDBRepository;
    private final MongoTemplate mongoTemplate;
    private Integer contagemPomodoroPausaCurta = 0;

    @Override
    public Tarefa salva(Tarefa tarefa) {
        log.info("[inicia] TarefaInfraRepository - salva");
        try {
            tarefaSpringMongoDBRepository.save(tarefa);
        } catch (DataIntegrityViolationException e) {
            throw APIException.build(HttpStatus.BAD_REQUEST, "Tarefa já cadastrada", e);
        }
        log.info("[finaliza] TarefaInfraRepository - salva");
        return tarefa;
    }

    @Override
    public Optional<Tarefa> buscaTarefaPorId(UUID idTarefa) {
        log.info("[inicia] TarefaInfraRepository - buscaTarefaPorId");
        Optional<Tarefa> tarefaPorId = tarefaSpringMongoDBRepository.findByIdTarefa(idTarefa);
        log.info("[finaliza] TarefaInfraRepository - buscaTarefaPorId");
        return tarefaPorId;
    }

    @Override
    public boolean deletaConcluidas(UUID idUsuario, StatusTarefa concluida) {
        log.info("[inicia] TarefaInfraRepository - deletaConcluidas");
        long deletedCount = tarefaSpringMongoDBRepository.deleteByIdUsuarioAndStatus(idUsuario, StatusTarefa.CONCLUIDA);
        log.info("[inicia] TarefaInfraRepository - deletaConcluidas");
        return deletedCount > 0;
    }

    @Override
    public void desativaTarefasId(UUID idUsuario) {
        log.info("[inicial] TarefaInfraRepository - desativaTarefasId");
        List<Tarefa> tarefasAtivas = tarefaSpringMongoDBRepository.findTarefasAtivasByUsuario(idUsuario);
        tarefasAtivas.stream().forEach(tarefa -> {
            tarefa.desativaTarefas();
            salva(tarefa);
        });
        log.info("[finaliza] TarefaInfraRepository - desativaTarefasId");
    }

    @Override
    public void deletaTodasAsTarefasDoUsuario(List<Tarefa> tarefasUsuario) {
        log.info("[inicia] TarefaInfraRepository - deletaTodasAsTarefasDoUsuario");
        tarefaSpringMongoDBRepository.deleteAll(tarefasUsuario);
        log.info("[finaliza] TarefaInfraRepository - deletaTodasAsTarefasDoUsuario");
    }

    @Override
    public List<Tarefa> buscaTarefasPorUsuario(UUID idUsuario) {
        log.info("[inicia] TarefaInfraRepository - buscaTarefaPorUsuario");
        List<Tarefa> buscaTodasAsTarefas = tarefaSpringMongoDBRepository.findAllByIdUsuario(idUsuario);
        log.info("[finaliza] TarefaInfraRepository - buscaTarefaPorUsuario");
        return buscaTodasAsTarefas;
    }

    @Override
    public void deletaTarefa(Tarefa tarefa) {
        log.info("[inicia] TarefaInfraRepository - deletaTarefa");
        tarefaSpringMongoDBRepository.delete(tarefa);
        log.info("[finaliza] TarefaInfraRepository - deletaTarefa");
    }

    @Override
    public void processaStatusEContadorPomodoro(Usuario usuarioPorEmail) {
        log.info("[inicia] - TarefaInfraRepository - processaStatusEContadorPomodoro");
        if (usuarioPorEmail.getStatus().equals(StatusUsuario.FOCO)) {
            if (this.contagemPomodoroPausaCurta < 3) {
                usuarioPorEmail.mudaStatusPausaCurta();
            } else {
                usuarioPorEmail.mudaStatusParaPausaLonga();
                this.contagemPomodoroPausaCurta = 0;
            }
        } else {
            usuarioPorEmail.alteraStatusParaFoco(usuarioPorEmail.getIdUsuario());
            this.contagemPomodoroPausaCurta++;
        }
        Query query = Query.query(Criteria.where("idUsuario").is(usuarioPorEmail.getIdUsuario()));
        Update updateUsuario = Update.update("status", usuarioPorEmail.getStatus());
        mongoTemplate.updateMulti(query, updateUsuario, Usuario.class);
        log.info("[finaliza] - TarefaInfraRepository - processaStatusEContadorPomodoro");
    }
}
