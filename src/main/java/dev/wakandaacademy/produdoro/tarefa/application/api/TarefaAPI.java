package dev.wakandaacademy.produdoro.tarefa.application.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;



@RestController
@RequestMapping("/v1/tarefa")
public interface TarefaAPI {
        @PostMapping
        @ResponseStatus(code = HttpStatus.CREATED)
        TarefaIdResponse postNovaTarefa(@RequestBody @Valid TarefaRequest tarefaRequest);

    @GetMapping("/{idTarefa}")
    @ResponseStatus(code = HttpStatus.OK)
    TarefaDetalhadoResponse detalhaTarefa(@RequestHeader(name = "Authorization", required = true) String token,@PathVariable UUID idTarefa);

    @PatchMapping("/usuario-modifica-a-ordem-de-uma-tarefa/{idTarefa}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    void mudaOrdemDeUmaTarefa(@RequestHeader(name = "Authorization", required = true) String token,@PathVariable UUID idTarefa, @RequestBody @Valid TarefaNovaPosicaoRequest tarefaNovaPosicaoRequest);


        @PatchMapping("/editaTarefa/{idTarefa}")
        @ResponseStatus(code = HttpStatus.NO_CONTENT)
        void editaTarefa(@RequestHeader(name = "Authorization", required = true) String token,
                        @PathVariable UUID idTarefa,
                        @RequestBody @Valid EditaTarefaRequest tarefaRequest);

        @GetMapping("/buscaTarefasDoUsuario/{idUsuario}")
        List<TarefaListResponse> buscaTarefasPorUsuario(
                        @RequestHeader(name = "Authorization", required = true) String token,
                        @PathVariable UUID idUsuario);

    @PatchMapping("/{idTarefa}/concluiTarefa")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    void concluiTarefa(@RequestHeader(name = "Authorization", required = true) String token,@PathVariable UUID idTarefa);

        @DeleteMapping("/usuario/{idUsuario}/deletaTarefasConcluidas")
        @ResponseStatus(code = HttpStatus.NO_CONTENT)
        void deletaTarefasConcluidas(@RequestHeader(name = "Authorization", required = true) String token,
                        @PathVariable UUID idUsuario);

        @DeleteMapping("/usuario/{idUsuario}/limpar-todas-as-tarefas")
        @ResponseStatus(code = HttpStatus.NO_CONTENT)
        void deletaTodasAsTarefasDoUsuario(@RequestHeader(name = "Authorization", required = true) String token,
                        @PathVariable UUID idUsuario);

        @PatchMapping("/AtivaTarefa/{idTarefa}")
        @ResponseStatus(code = HttpStatus.NO_CONTENT)
        void ativaTarefa(@RequestHeader(name = "Authorization", required = true) String token,
                        @PathVariable UUID idTarefa);

        @DeleteMapping("/{idTarefa}/deletaTarefa")
        @ResponseStatus(code = HttpStatus.NO_CONTENT)
        void deletaTarefa(@RequestHeader(name = "Authorization", required = true) String token,
                        @PathVariable UUID idTarefa);

        @PatchMapping("/{idTarefa}/incrementa-pomodoro")
        @ResponseStatus(code = HttpStatus.NO_CONTENT)
        void imcrementaPomodoro(@RequestHeader(name = "Authorization", required = true) String token,
                        @PathVariable UUID idTarefa);

}