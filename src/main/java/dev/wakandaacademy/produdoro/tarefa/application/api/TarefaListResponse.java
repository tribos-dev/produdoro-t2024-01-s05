package dev.wakandaacademy.produdoro.tarefa.application.api;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import dev.wakandaacademy.produdoro.tarefa.domain.StatusAtivacaoTarefa;
import dev.wakandaacademy.produdoro.tarefa.domain.StatusTarefa;
import dev.wakandaacademy.produdoro.tarefa.domain.Tarefa;
import lombok.Value;

@Value
public class TarefaListResponse {
	private UUID idTarefa;
	private String descricao;
	private UUID idUsuario;
	private UUID idArea;
	private UUID idProjeto;
	private StatusTarefa status;
	private StatusAtivacaoTarefa statusAtivacao;
	private int contagemPomodoro;
	
	public TarefaListResponse(Tarefa tarefa) {
		this.idTarefa = tarefa.getIdTarefa();
		this.idUsuario = tarefa.getIdUsuario();
		this.descricao = tarefa.getDescricao();
		this.idArea = tarefa.getIdArea();
		this.idProjeto = tarefa.getIdProjeto();
		this.status = tarefa.getStatus();
		this.statusAtivacao = tarefa.getStatusAtivacao();
		this.contagemPomodoro = 1;
	}
	
	public static List<TarefaListResponse> converte(List<Tarefa> tarefas) {
		return tarefas.stream().map(TarefaListResponse::new).collect(Collectors.toList());
	}
}
