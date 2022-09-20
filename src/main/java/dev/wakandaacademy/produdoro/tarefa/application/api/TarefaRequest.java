package dev.wakandaacademy.produdoro.tarefa.application.api;

import lombok.NonNull;
import lombok.Value;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;

@Value
public class TarefaRequest {

    @NotBlank
    @Size(message = "Campo descrição tarefa não pode estar vazio", max = 255, min = 3)
    private String descricao;
    @NonNull
    private UUID idUsuario;
    private UUID idArea;
    private UUID idProjeto;
    private int contagemPomodoro;

}
