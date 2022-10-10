package dev.wakandaacademy.produdoro.tarefa.application.api;

import dev.wakandaacademy.produdoro.tarefa.application.service.TarefaService;
import dev.wakandaacademy.produdoro.tarefa.domain.Tarefa;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@Log4j2
@RequiredArgsConstructor
public class TarefaRestController implements TarefaAPI {

    private final TarefaService tarefaService;

    public TarefaIdResponse postNovaTarefa(TarefaRequest tarefaRequest) {
        log.info("[inicia]  TarefaRestController - postNovaTarefa  ");
        TarefaIdResponse tarefaCriada = tarefaService.criaNovaTarefa(tarefaRequest);
        log.info("[finaliza]  TarefaRestController - postNovaTarefa");
        return tarefaCriada;
    }
    @Override
    public TarefaDetalhadoResponse detalhaTarefa(UUID idTarefa) {
        log.info("[inicia] TarefaRestController - detalhaTarefa");
        Tarefa tarefa = tarefaService.detalhaTarefa(idTarefa);
        log.info("[finaliza] TarefaRestController - detalhaTarefa");
        return new TarefaDetalhadoResponse(tarefa);
    }

}
