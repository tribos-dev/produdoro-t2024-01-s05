package dev.wakandaacademy.produdoro.tarefa.application.api;

import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.mongodb.lang.NonNull;

import lombok.Value;
@Value
public class ConcluiTarefaRequest {

    @NotBlank
    @Size(message = "Campo descrição tarefa não pode estar vazio", max = 255, min = 3)
    private String descricao;
    @NonNull
    private UUID idUsuario;
    private UUID idArea;
    private UUID idProjeto;
    private int contagemPomodoro;

}
