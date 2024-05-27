package dev.wakandaacademy.produdoro.tarefa.application.repository;


import dev.wakandaacademy.produdoro.tarefa.application.api.TarefaNovaPosicaoRequest;

import dev.wakandaacademy.produdoro.tarefa.domain.StatusTarefa;

import dev.wakandaacademy.produdoro.tarefa.domain.Tarefa;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TarefaRepository {
    Tarefa salva(Tarefa tarefa);

    Optional<Tarefa> buscaTarefaPorId(UUID idTarefa);


    void mudaOrdemDeUmaTarefa( Tarefa tarefa,List<Tarefa> tarefas,TarefaNovaPosicaoRequest tarefaNovaPosicaoRequest);


    int contarTarefas(UUID idUsuario);

    void deletaTarefa(Tarefa tarefa);

    boolean deletaConcluidas(UUID idUsuario, StatusTarefa concluida);

    void desativaTarefasId(UUID idUsuario);

    void deletaTodasAsTarefasDoUsuario(List<Tarefa> tarefasUsuario);

    List<Tarefa> buscaTarefasPorUsuario(UUID idUsuario);
}
