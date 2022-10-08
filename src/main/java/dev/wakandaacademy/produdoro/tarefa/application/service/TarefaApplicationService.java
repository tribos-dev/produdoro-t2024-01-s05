package dev.wakandaacademy.produdoro.tarefa.application.service;

import dev.wakandaacademy.produdoro.tarefa.application.api.TarefaIdResponse;
import dev.wakandaacademy.produdoro.tarefa.application.api.TarefaListResponse;
import dev.wakandaacademy.produdoro.tarefa.application.api.TarefaRequest;
import dev.wakandaacademy.produdoro.tarefa.application.repository.TarefaRepository;
import dev.wakandaacademy.produdoro.tarefa.domain.Tarefa;
import dev.wakandaacademy.produdoro.usuario.application.service.UsuarioService;
import dev.wakandaacademy.produdoro.usuario.domain.Usuario;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Log4j2
@RequiredArgsConstructor
public class TarefaApplicationService implements TarefaService {
    private final TarefaRepository tarefaRepository;
    private final UsuarioService usuarioService;

    @Override
    public TarefaIdResponse criaNovaTarefa(TarefaRequest tarefaRequest) {
        log.info("[start] TarefaSpringMongoDBService - criaNovaTarefa");
        Tarefa tarefaCriada = tarefaRepository.salva(new Tarefa(tarefaRequest));
        log.info("[finish] TarefaSpringMongoDBService - criaNovaTarefa");
        return TarefaIdResponse.builder().idTarefa(tarefaCriada.getIdTarefa()).build();
    }

    @Override
    public List<TarefaListResponse> buscarTarefasPorIdUsuario(Optional<String> idUsuario) {
        log.info("[inicia] TarefaApplicationService - buscarTarefasPorIdUsuario");
        String id;
        if (idUsuario.isPresent()) {
            id = idUsuario.get();
            Usuario usuario = usuarioService.buscaUsuarioAtravesId(UUID.fromString(id));
            List<Tarefa> listaDeTarefa = tarefaRepository.buscarTarefasPorIdUsuario(usuario.getIdUsuario());
            log.info("[finaliza] TarefaApplicationService - buscarTarefasPorIdUsuario");
            return TarefaListResponse.converte(listaDeTarefa);
        }
        return Arrays.asList();
    }
}
