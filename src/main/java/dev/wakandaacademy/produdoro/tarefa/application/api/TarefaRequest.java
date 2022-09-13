package dev.wakandaacademy.produdoro.tarefa.application.api;

import lombok.Value;

import java.util.UUID;

@Value
public class TarefaRequest {

    private String descricao;
    private UUID idUsuario;
    private UUID idArea;
    private UUID idProjeto;

}
