package dev.wakandaacademy.produdoro.tarefa.infra;

import dev.wakandaacademy.produdoro.handler.APIException;
import dev.wakandaacademy.produdoro.tarefa.application.api.TarefaNovaPosicaoRequest;
import dev.wakandaacademy.produdoro.tarefa.application.repository.TarefaRepository;
import dev.wakandaacademy.produdoro.tarefa.domain.Tarefa;
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
import java.util.stream.IntStream;

@Repository
@Log4j2
@RequiredArgsConstructor
public class TarefaInfraRepository implements TarefaRepository {

    private final TarefaSpringMongoDBRepository tarefaSpringMongoDBRepository;
    private final MongoTemplate mongoTemplate;

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
    public void mudaOrdemDeUmaTarefa(Tarefa tarefa, List<Tarefa> tarefas, TarefaNovaPosicaoRequest tarefaNovaPosicaoRequest) {
        log.info("[inicia] TarefaInfraRepository - mudaOrdemDeUmaTarefa");
        int posicaoAtual = tarefa.getPosicao();
        int novaPosicao = tarefaNovaPosicaoRequest.getNovaPosicao();

        if (novaPosicao < posicaoAtual) {
            IntStream.range(novaPosicao, posicaoAtual).forEach(i -> atualizaPosicao(tarefas.get(i), i + 1));
        } else if (novaPosicao > posicaoAtual) {
            IntStream.range(posicaoAtual + 1, novaPosicao + 1).forEach(i -> atualizaPosicao(tarefas.get(i), i - 1));
        }
        tarefa.atualizaPosicaoTarefa(novaPosicao);
        atualizaPosicao(tarefa,novaPosicao);
        log.info("[finaliza] TarefaInfraRepository - mudaOrdemDeUmaTarefa");
    }

    private void atualizaPosicao(Tarefa tarefa, int novaPosicao) {
        Query query = new Query(Criteria.where("idTarefa").is(tarefa.getIdTarefa()));
        Update update = new Update().set("posicao", novaPosicao);
        mongoTemplate.updateFirst(query, update, Tarefa.class);
    }

    private void validaNovaPosicao(List<Tarefa> tarefas, Tarefa tarefa, TarefaNovaPosicaoRequest tarefaNovaPosicaoRequest) {
        int posicaoAntiga = tarefa.getPosicao();

        int tamanhoListaTarefas = tarefas.size();
        if (tarefaNovaPosicaoRequest.getNovaPosicao() >= tamanhoListaTarefas || tarefaNovaPosicaoRequest.getNovaPosicao().equals(posicaoAntiga)) {
            String mensagem = tarefaNovaPosicaoRequest.getNovaPosicao() >= tamanhoListaTarefas
                    ? "a posicao da tarefa nao pode ser maior nem igual a quantidade de tarefas do usuario "
                    : "a posicao enviada é igual a posicao atual da tarefa";
            throw APIException.build(HttpStatus.BAD_REQUEST, mensagem);
        }


    }


    @Override
    public int contarTarefas(UUID idUsuario) {
        log.info("[inicia] TarefaInfraRepository - contarTarefas");
        int contadorDeTarefas = tarefaSpringMongoDBRepository.countByIdUsuario(idUsuario);
        log.info("[finaliza] TarefaInfraRepository - contarTarefas");
        return contadorDeTarefas;
    }
}
