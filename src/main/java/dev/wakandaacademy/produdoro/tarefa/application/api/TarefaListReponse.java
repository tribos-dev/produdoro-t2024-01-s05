package dev.wakandaacademy.produdoro.tarefa.application.api;

import java.util.UUID;

import dev.wakandaacademy.produdoro.tarefa.domain.StatusAtivacaoTarefa;
import dev.wakandaacademy.produdoro.tarefa.domain.StatusTarefa;
import lombok.Value;

@Value
public class TarefaListReponse {
	private UUID idTarefa;
	private String descricao;
	private UUID idUsuario;
	private UUID idArea;
	private UUID idProjeto;
	private StatusTarefa status;
	private StatusAtivacaoTarefa statusAtivacao;
	private int contagemPomodoro;
}
