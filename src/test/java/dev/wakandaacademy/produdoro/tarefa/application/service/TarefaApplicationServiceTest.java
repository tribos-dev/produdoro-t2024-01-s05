package dev.wakandaacademy.produdoro.tarefa.application.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import dev.wakandaacademy.produdoro.DataHelper;
import dev.wakandaacademy.produdoro.handler.APIException;
import dev.wakandaacademy.produdoro.tarefa.application.api.TarefaIdResponse;
import dev.wakandaacademy.produdoro.tarefa.application.api.TarefaRequest;
import dev.wakandaacademy.produdoro.tarefa.application.repository.TarefaRepository;
import dev.wakandaacademy.produdoro.tarefa.domain.StatusTarefa;
import dev.wakandaacademy.produdoro.tarefa.domain.Tarefa;
import dev.wakandaacademy.produdoro.tarefa.infra.TarefaInfraRepository;
import dev.wakandaacademy.produdoro.usuario.application.repository.UsuarioRepository;
import dev.wakandaacademy.produdoro.usuario.application.service.UsuarioApplicationService;
import dev.wakandaacademy.produdoro.usuario.domain.Usuario;

@ExtendWith(MockitoExtension.class)
class TarefaApplicationServiceTest {

	// @Autowired
	@InjectMocks
	TarefaApplicationService tarefaApplicationService;
	
	@InjectMocks
    UsuarioApplicationService usuarioApplicationService;

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

        assertDoesNotThrow(() -> tarefaApplicationService.deletaTarefasConcluidas(usuario.getEmail(), usuario.getIdUsuario()));

        verify(usuarioRepository).buscaUsuarioPorId(usuario.getIdUsuario());
        verify(usuarioRepository).buscaUsuarioPorEmail(usuario.getEmail());
        verify(tarefaRepository).deletaConcluidas(usuario.getIdUsuario(), StatusTarefa.CONCLUIDA);
    }
	
	@Test
    public void testDeletaTarefasConcluidas_UsuarioSemTarefasConcluidas() {
		Usuario usuario = DataHelper.createUsuario();    
  
		when(usuarioRepository.buscaUsuarioPorId(any(UUID.class))).thenReturn(usuario);
		when(usuarioRepository.buscaUsuarioPorEmail(anyString())).thenReturn(usuario);       
        when(tarefaRepository.deletaConcluidas(usuario.getIdUsuario(), StatusTarefa.CONCLUIDA)).thenReturn(false);

        APIException exception = assertThrows(APIException.class, () -> 
        	tarefaApplicationService.deletaTarefasConcluidas(usuario.getEmail(), usuario.getIdUsuario())
        );

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
		 String usuarioEmailInvalido  = "invalid@example.com";
		 UUID idUsuario = usuario.getIdUsuario();
		 
	        // Arrange
	        when(usuarioRepository.buscaUsuarioPorId(idUsuario)).thenReturn(usuario);
	        when(usuarioRepository.buscaUsuarioPorEmail(usuarioEmailInvalido)).thenReturn(null);

	        // Act & Assert
	        assertThrows(APIException.class, () -> {
	        	tarefaApplicationService.deletaTarefasConcluidas(usuarioEmailInvalido , idUsuario);
	        });

	        // Verify
	        verify(usuarioRepository).buscaUsuarioPorId(idUsuario);
	        verify(usuarioRepository).buscaUsuarioPorEmail(usuarioEmailInvalido );
	    }

}
