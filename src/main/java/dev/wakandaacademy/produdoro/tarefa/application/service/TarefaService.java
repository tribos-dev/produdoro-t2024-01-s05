package dev.wakandaacademy.produdoro.tarefa.application.service;

import java.util.UUID;

import dev.wakandaacademy.produdoro.tarefa.application.api.TarefaIdResponse;
import dev.wakandaacademy.produdoro.tarefa.application.api.TarefaListResponse;
import dev.wakandaacademy.produdoro.tarefa.application.api.TarefaRequest;
import dev.wakandaacademy.produdoro.tarefa.domain.Tarefa;

import java.util.List;
import java.util.UUID;
public interface TarefaService {
    TarefaIdResponse criaNovaTarefa(TarefaRequest tarefaRequest);
    Tarefa detalhaTarefa(String usuario, UUID idTarefa);
	List<TarefaListResponse> buscaTarefasPorUsuario(String usuario, UUID idUsuario);
	void concluiTarefa(String emailUsuario, UUID idTarefa);
}
