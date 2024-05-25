package dev.wakandaacademy.produdoro.tarefa.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import dev.wakandaacademy.produdoro.handler.APIException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.wakandaacademy.produdoro.DataHelper;
import dev.wakandaacademy.produdoro.tarefa.application.api.TarefaIdResponse;
import dev.wakandaacademy.produdoro.tarefa.application.api.TarefaRequest;
import dev.wakandaacademy.produdoro.tarefa.application.repository.TarefaRepository;
import dev.wakandaacademy.produdoro.tarefa.domain.StatusTarefa;
import dev.wakandaacademy.produdoro.tarefa.domain.Tarefa;
import dev.wakandaacademy.produdoro.usuario.application.repository.UsuarioRepository;
import dev.wakandaacademy.produdoro.usuario.domain.Usuario;
import org.springframework.http.HttpStatus;

@ExtendWith(MockitoExtension.class)
class TarefaApplicationServiceTest {

	// @Autowired
	@InjectMocks
	TarefaApplicationService tarefaApplicationService;

	// @MockBean
	@Mock
	TarefaRepository tarefaRepository;
	@Mock
	UsuarioRepository usuarioRepository;

	@Test
	void deveRetornarIdTarefaNovaCriada() {
		TarefaRequest request = getTarefaRequest();
		when(tarefaRepository.salva(any())).thenReturn(new Tarefa(request));

		TarefaIdResponse response = tarefaApplicationService.criaNovaTarefa(request);

		assertNotNull(response);
		assertEquals(TarefaIdResponse.class, response.getClass());
		assertEquals(UUID.class, response.getIdTarefa().getClass());
	}

	@Test
	void deveConcluirTarefa() {
		Usuario usuario = DataHelper.createUsuario();
		Tarefa tarefa = DataHelper.createTarefa();
		when(usuarioRepository.buscaUsuarioPorEmail(any())).thenReturn(usuario);
		when(tarefaRepository.buscaTarefaPorId(any())).thenReturn(Optional.of(tarefa));
		tarefaApplicationService.concluiTarefa(usuario.getEmail(), tarefa.getIdTarefa());
		assertEquals(tarefa.getStatus(), StatusTarefa.CONCLUIDA);
	}

	@Test
	void deveDeletarTodasAsTarefasDoUsuario() {
		Usuario usuario = DataHelper.createUsuario();
		List<Tarefa> tarefas = DataHelper.createListTarefa();
		when(usuarioRepository.buscaUsuarioPorEmail(any())).thenReturn(usuario);
		when(usuarioRepository.buscaUsuarioPorId(any())).thenReturn(usuario);
		when(tarefaRepository.buscarTodasTarefasPorIdUsuario(any())).thenReturn(tarefas);
		tarefaApplicationService.deletaTodasAsTarefasDoUsuario(usuario.getEmail(),usuario.getIdUsuario());
		verify(tarefaRepository,times(1)).deletaTodasAsTarefasDoUsuario(tarefas);
	}

	@Test
	void deletaTodasAsTarefasDoUsuario_quandoUsuarioNaoPossuiTarefaCadastrada_retornaAPIException() {
		Usuario usuario = DataHelper.createUsuario();
		String email = usuario.getEmail();
		UUID idUsuario = usuario.getIdUsuario();
		List<Tarefa> listaVazia = List.of();
		when(usuarioRepository.buscaUsuarioPorEmail(any())).thenReturn(usuario);
		when(usuarioRepository.buscaUsuarioPorId(any())).thenReturn(usuario);
		when(tarefaRepository.buscarTodasTarefasPorIdUsuario(any())).thenReturn(listaVazia);

		APIException ex = assertThrows(APIException.class,
				() -> tarefaApplicationService.deletaTodasAsTarefasDoUsuario(email, idUsuario));

		assertEquals("Usuário não possui tarefa(as) cadastrada(as)", ex.getMessage());
		assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusException());
	}

	public TarefaRequest getTarefaRequest() {
		TarefaRequest request = new TarefaRequest("tarefa 1", UUID.randomUUID(), null, null, 0);
		return request;
	}
}
