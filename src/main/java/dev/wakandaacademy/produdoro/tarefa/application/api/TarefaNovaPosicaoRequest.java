package dev.wakandaacademy.produdoro.tarefa.application.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Value
public class TarefaNovaPosicaoRequest {
    @PositiveOrZero
    @NotNull
    private Integer novaPosicao;

    public TarefaNovaPosicaoRequest(@JsonProperty("novaPosicao") Integer novaPosicao){
        this.novaPosicao = novaPosicao;
    }


}
