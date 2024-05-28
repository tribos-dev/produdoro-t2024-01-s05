package dev.wakandaacademy.produdoro.tarefa.application.repository;

import dev.wakandaacademy.produdoro.tarefa.domain.StatusTarefa;
import dev.wakandaacademy.produdoro.tarefa.domain.Tarefa;
import dev.wakandaacademy.produdoro.usuario.domain.Usuario;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TarefaRepository {
    Tarefa salva(Tarefa tarefa);

    Optional<Tarefa> buscaTarefaPorId(UUID idTarefa);

    void deletaTarefa(Tarefa tarefa);

    boolean deletaConcluidas(UUID idUsuario, StatusTarefa concluida);

    void desativaTarefasId(UUID idUsuario);

    void deletaTodasAsTarefasDoUsuario(List<Tarefa> tarefasUsuario);

    List<Tarefa> buscaTarefasPorUsuario(UUID idUsuario);

    void processaStatusEContadorPomodoro(Usuario usuarioPorEmail);
}
