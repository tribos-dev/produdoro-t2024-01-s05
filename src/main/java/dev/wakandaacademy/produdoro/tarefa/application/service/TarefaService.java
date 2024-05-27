package dev.wakandaacademy.produdoro.tarefa.application.service;

import java.util.List;
import java.util.UUID;

import dev.wakandaacademy.produdoro.tarefa.application.api.EditaTarefaRequest;
import dev.wakandaacademy.produdoro.tarefa.application.api.TarefaIdResponse;

import dev.wakandaacademy.produdoro.tarefa.application.api.TarefaNovaPosicaoRequest;

import dev.wakandaacademy.produdoro.tarefa.application.api.TarefaListResponse;

import dev.wakandaacademy.produdoro.tarefa.application.api.TarefaRequest;
import dev.wakandaacademy.produdoro.tarefa.domain.Tarefa;

public interface TarefaService {
    TarefaIdResponse criaNovaTarefa(TarefaRequest tarefaRequest);

    Tarefa detalhaTarefa(String usuario, UUID idTarefa);

	void concluiTarefa(String emailUsuario, UUID idTarefa);

    void mudaOrdemDeUmaTarefa(String email, UUID idTarefa, TarefaNovaPosicaoRequest tarefaNovaPosicaoRequest);


    void deletaTarefa(String emailUsuario, UUID idTarefa);

    void ativaTarefa(String usuario, UUID idTarefa);

    void editaTarefa(String emailUsuario, UUID idTarefa, EditaTarefaRequest tarefaRequest);

    List<TarefaListResponse> buscaTarefasPorUsuario(String usuario, UUID idUsuario);
    
    void deletaTodasAsTarefasDoUsuario(String emailUsuario, UUID idUsuario);

    void deletaTarefasConcluidas(String usuario, UUID idUsuario);
}

