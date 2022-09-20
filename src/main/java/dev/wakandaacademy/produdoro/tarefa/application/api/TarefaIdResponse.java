package dev.wakandaacademy.produdoro.tarefa.application.api;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class TarefaIdResponse {
    private UUID idTarefa;
}
