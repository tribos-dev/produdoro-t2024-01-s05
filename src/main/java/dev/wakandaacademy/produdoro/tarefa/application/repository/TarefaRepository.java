package dev.wakandaacademy.produdoro.tarefa.application.repository;

import dev.wakandaacademy.produdoro.tarefa.domain.StatusTarefa;
import dev.wakandaacademy.produdoro.tarefa.domain.Tarefa;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import dev.wakandaacademy.produdoro.tarefa.domain.Tarefa;

public interface TarefaRepository {
    Tarefa salva(Tarefa tarefa);

    Optional<Tarefa> buscaTarefaPorId(UUID idTarefa);

    boolean deletaConcluidas(UUID idUsuario, StatusTarefa concluida);

    void desativaTarefasId(UUID idUsuario);

    void deletaTodasAsTarefasDoUsuario(List<Tarefa> tarefasUsuario);

    List<Tarefa> buscaTarefasPorUsuario(UUID idUsuario);
}
