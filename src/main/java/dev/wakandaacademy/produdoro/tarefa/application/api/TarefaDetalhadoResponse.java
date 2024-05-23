package dev.wakandaacademy.produdoro.tarefa.application.api;

import dev.wakandaacademy.produdoro.tarefa.domain.StatusAtivacaoTarefa;
import dev.wakandaacademy.produdoro.tarefa.domain.StatusTarefa;
import dev.wakandaacademy.produdoro.tarefa.domain.Tarefa;
import lombok.Value;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Value
public class TarefaDetalhadoResponse {

    private UUID idTarefa;
    private String descricao;
    private UUID idUsuario;
    private UUID idArea;
    private UUID idProjeto;
    private StatusTarefa status;
    private StatusAtivacaoTarefa statusAtivacao;
    private int contagemPomodoro;
    private int posicao;

    public TarefaDetalhadoResponse(Tarefa tarefa) {
        this.idTarefa = tarefa.getIdTarefa();
        this.descricao = tarefa.getDescricao();
        this.idUsuario = tarefa.getIdUsuario();
        this.idArea = tarefa.getIdArea();
        this.idProjeto = tarefa.getIdProjeto();
        this.status = tarefa.getStatus();
        this.statusAtivacao = tarefa.getStatusAtivacao();
        this.contagemPomodoro = tarefa.getContagemPomodoro();
        this.posicao = tarefa.getPosicao();
    }
}
