package dev.wakandaacademy.produdoro.tarefa.application.service;

import dev.wakandaacademy.produdoro.DataHelper;
import dev.wakandaacademy.produdoro.handler.APIException;
import dev.wakandaacademy.produdoro.tarefa.application.api.*;
import dev.wakandaacademy.produdoro.tarefa.application.repository.TarefaRepository;
import dev.wakandaacademy.produdoro.tarefa.domain.StatusAtivacaoTarefa;
import dev.wakandaacademy.produdoro.tarefa.domain.StatusTarefa;
import dev.wakandaacademy.produdoro.tarefa.domain.Tarefa;
import dev.wakandaacademy.produdoro.usuario.application.repository.UsuarioRepository;
import dev.wakandaacademy.produdoro.usuario.domain.Usuario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TarefaApplicationServiceTest {

	// @Autowired
	@InjectMocks
	TarefaApplicationService tarefaApplicationService;
	// @MockBean
	@Mock
	TarefaRepository tarefaRepository;

	// @MockBean
	@Mock
	UsuarioRepository usuarioRepository;

	@Test
	void deveRetornarIdTarefaNovaCriada() {
		TarefaRequest request = getTarefaRequest();
		int novaPosicao = tarefaRepository.contarTarefas( getTarefaRequest().getIdUsuario());
		when(tarefaRepository.salva(any())).thenReturn(new Tarefa(request,novaPosicao));

		TarefaIdResponse response = tarefaApplicationService.criaNovaTarefa(request);

		assertNotNull(response);
		assertEquals(TarefaIdResponse.class, response.getClass());
		assertEquals(UUID.class, response.getIdTarefa().getClass());
	}

	@Test
	void deveRetornarTarefaAtiva() {
		Usuario usuarioRequest = DataHelper.createUsuario();
		Tarefa tarefa = DataHelper.createTarefa();
		when(usuarioRepository.buscaUsuarioPorEmail(any())).thenReturn(usuarioRequest);
		when(tarefaRepository.buscaTarefaPorId(any())).thenReturn(Optional.of(tarefa));
		tarefaApplicationService.ativaTarefa(usuarioRequest.getEmail(), tarefa.getIdTarefa());

		verify(tarefaRepository, times(1)).buscaTarefaPorId(tarefa.getIdTarefa());
		assertEquals(StatusAtivacaoTarefa.ATIVA, tarefa.getStatusAtivacao());
	}

	@Test
	void deveEditarTarefa() {
		Usuario usuarioEmail = DataHelper.createUsuario();
		Tarefa tarefaRequest = DataHelper.createTarefa();
		EditaTarefaRequest request = DataHelper.alteraTarefa();
		when(usuarioRepository.buscaUsuarioPorEmail(any())).thenReturn(usuarioEmail);
		when(tarefaRepository.buscaTarefaPorId(any())).thenReturn(Optional.of(tarefaRequest));
		tarefaApplicationService.editaTarefa(usuarioEmail.getEmail(), tarefaRequest.getIdTarefa(), request);
		verify(usuarioRepository, times(1)).buscaUsuarioPorEmail(usuarioEmail.getEmail());
		verify(tarefaRepository, times(1)).buscaTarefaPorId(tarefaRequest.getIdTarefa());
		assertEquals("Tarefa alterada", tarefaRequest.getDescricao());
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
		when(tarefaRepository.buscaTarefasPorUsuario(any())).thenReturn(tarefas);
		tarefaApplicationService.deletaTodasAsTarefasDoUsuario(usuario.getEmail(), usuario.getIdUsuario());
		verify(tarefaRepository, times(1)).deletaTodasAsTarefasDoUsuario(tarefas);
	}

	@Test
	void deletaTodasAsTarefasDoUsuario_quandoUsuarioNaoPossuiTarefaCadastrada_retornaAPIException() {
		Usuario usuario = DataHelper.createUsuario();
		String email = usuario.getEmail();
		UUID idUsuario = usuario.getIdUsuario();
		List<Tarefa> listaVazia = List.of();
		when(usuarioRepository.buscaUsuarioPorEmail(any())).thenReturn(usuario);
		when(usuarioRepository.buscaUsuarioPorId(any())).thenReturn(usuario);
		when(tarefaRepository.buscaTarefasPorUsuario(any())).thenReturn(listaVazia);

		APIException ex = assertThrows(APIException.class,
				() -> tarefaApplicationService.deletaTodasAsTarefasDoUsuario(email, idUsuario));

		assertEquals("Usuário não possui tarefa(as) cadastrada(as)", ex.getMessage());
		assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusException());
	}

	public TarefaRequest getTarefaRequest() {
		TarefaRequest request = new TarefaRequest("tarefa 1", UUID.randomUUID(), null, null, 0);
		return request;
	}

	@Test
	public void testDeletaTarefasConcluidas_Success() {
		Usuario usuario = DataHelper.createUsuario();

		when(usuarioRepository.buscaUsuarioPorId(any(UUID.class))).thenReturn(usuario);
		when(usuarioRepository.buscaUsuarioPorEmail(anyString())).thenReturn(usuario);
		when(tarefaRepository.deletaConcluidas(usuario.getIdUsuario(), StatusTarefa.CONCLUIDA)).thenReturn(true);

		assertDoesNotThrow(
				() -> tarefaApplicationService.deletaTarefasConcluidas(usuario.getEmail(), usuario.getIdUsuario()));

		verify(usuarioRepository).buscaUsuarioPorId(usuario.getIdUsuario());
		verify(usuarioRepository).buscaUsuarioPorEmail(usuario.getEmail());
		verify(tarefaRepository).deletaConcluidas(usuario.getIdUsuario(), StatusTarefa.CONCLUIDA);
	}

	@Test
	void deveDeletarTarefa() {
		Usuario usuario = DataHelper.createUsuario();
		Tarefa tarefa = DataHelper.createTarefa();
		when(usuarioRepository.buscaUsuarioPorEmail(any())).thenReturn(usuario);
		when(tarefaRepository.buscaTarefaPorId(any())).thenReturn(Optional.of(tarefa));

		tarefaApplicationService.deletaTarefa(usuario.getEmail(), tarefa.getIdTarefa());
		verify(usuarioRepository, times(1)).buscaUsuarioPorEmail(usuario.getEmail());
		verify(tarefaRepository, times(1)).buscaTarefaPorId(tarefa.getIdTarefa());
	}

	@Test
	public void testDeletaTarefasConcluidas_UsuarioSemTarefasConcluidas() {
		Usuario usuario = DataHelper.createUsuario();

		when(usuarioRepository.buscaUsuarioPorId(any(UUID.class))).thenReturn(usuario);
		when(usuarioRepository.buscaUsuarioPorEmail(anyString())).thenReturn(usuario);
		when(tarefaRepository.deletaConcluidas(usuario.getIdUsuario(), StatusTarefa.CONCLUIDA)).thenReturn(false);

		APIException exception = assertThrows(APIException.class,
				() -> tarefaApplicationService.deletaTarefasConcluidas(usuario.getEmail(), usuario.getIdUsuario()));

		assertEquals(HttpStatus.NOT_FOUND, exception.getStatusException());
		assertEquals("Usuário não possui nenhuma tarefa concluída!", exception.getMessage());
	}

	@Test
	public void testDeletaTarefasConcluidas_UsuarioComIdInvalido() {
		Usuario usuario = DataHelper.createUsuario();
		String email = usuario.getEmail();
		UUID idInvalido = UUID.randomUUID();

		when(usuarioRepository.buscaUsuarioPorId(any()))
				.thenThrow(APIException.build(HttpStatus.NOT_FOUND, "Usuario não encontrado!"));

		assertThrows(APIException.class, () -> {
			tarefaApplicationService.deletaTarefasConcluidas(email, idInvalido);
		});

		verify(usuarioRepository).buscaUsuarioPorId(idInvalido);
	}

	public void testDeletaTarefasConcluidas_EmailUsuarioInvalido() {
		Usuario usuario = DataHelper.createUsuario();
		String usuarioEmailInvalido = "invalid@example.com";
		UUID idUsuario = usuario.getIdUsuario();

		// Arrange
		when(usuarioRepository.buscaUsuarioPorId(idUsuario)).thenReturn(usuario);
		when(usuarioRepository.buscaUsuarioPorEmail(usuarioEmailInvalido)).thenReturn(null);

		// Act & Assert
		assertThrows(APIException.class, () -> {
			tarefaApplicationService.deletaTarefasConcluidas(usuarioEmailInvalido, idUsuario);
		});

		// Verify
		verify(usuarioRepository).buscaUsuarioPorId(idUsuario);
		verify(usuarioRepository).buscaUsuarioPorEmail(usuarioEmailInvalido);
	}

	@Test
	public void testBuscaTodasTarefasPorUsuario() {
		Usuario usuario = DataHelper.createUsuario();
		List<Tarefa> tarefas = DataHelper.createListTarefa();

		when(usuarioRepository.buscaUsuarioPorEmail(any())).thenReturn(usuario);
		when(usuarioRepository.buscaUsuarioPorId(any())).thenReturn(usuario);
		when(tarefaRepository.buscaTarefasPorUsuario(any())).thenReturn(tarefas);

		List<TarefaListResponse> resultado = tarefaApplicationService.buscaTarefasPorUsuario(usuario.getEmail(),
				usuario.getIdUsuario());

		verify(usuarioRepository, times(1)).buscaUsuarioPorEmail(usuario.getEmail());
		verify(usuarioRepository, times(1)).buscaUsuarioPorId(usuario.getIdUsuario());
		verify(tarefaRepository, times(1)).buscaTarefasPorUsuario(usuario.getIdUsuario());

		assertEquals(resultado.size(), 8);
	}

	@Test
	public void testNaoDeveBuscaTodasTarefasPorUsuario() {
		Usuario usuario = DataHelper.createUsuario();

		when(usuarioRepository.buscaUsuarioPorEmail(any()))
				.thenThrow(APIException.build(HttpStatus.BAD_REQUEST, "Usuario não encontrado!"));

		APIException e = assertThrows(APIException.class, () -> tarefaApplicationService
				.buscaTarefasPorUsuario("emailinvalido@gmail.com", usuario.getIdUsuario()));

		assertEquals(HttpStatus.BAD_REQUEST, e.getStatusException());
		assertEquals("Usuario não encontrado!", e.getMessage());
	}

	@Test
	public void testMudaOrdemDeUmaTarefa(){
		Usuario usuario = DataHelper.createUsuario();
		List<Tarefa> tarefas = DataHelper.createListTarefa();
		var posicao = DataHelper.novaPosicaoRequest(2);
		Tarefa tarefa =DataHelper.createTarefa();
		when(usuarioRepository.buscaUsuarioPorEmail(any())).thenReturn(usuario);
		when(tarefaRepository.buscaTarefaPorId(any())).thenReturn(Optional.of(tarefa));
		when(tarefaRepository.buscaTarefasPorUsuario(any())).thenReturn(tarefas);
		tarefaApplicationService.mudaOrdemDeUmaTarefa(usuario.getEmail(),tarefa.getIdTarefa(),posicao);
		verify(tarefaRepository,times(1)).mudaOrdemDeUmaTarefa(tarefa,tarefas,posicao);
	}
	@Test
	public void testNaoMudaOrdemDeUmaTarefa(){
		Usuario usuario = DataHelper.createUsuario();
		List<Tarefa> tarefas = DataHelper.createListTarefa();
		var posicao = DataHelper.novaPosicaoRequest(2);
		Tarefa tarefaNaoExiste =DataHelper.createTarefa();

		when(usuarioRepository.buscaUsuarioPorEmail(any())).thenReturn(usuario);
		when(tarefaRepository.buscaTarefaPorId(any())).thenReturn(Optional.empty());
		assertThrows(APIException.class,()->tarefaApplicationService.mudaOrdemDeUmaTarefa(usuario.getEmail(),tarefaNaoExiste.getIdTarefa(),posicao));
		verify(tarefaRepository,never()).mudaOrdemDeUmaTarefa(tarefaNaoExiste,tarefas,posicao);

	}

}
