package dev.wakandaacademy.produdoro.usuario.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import dev.wakandaacademy.produdoro.handler.APIException;
import dev.wakandaacademy.produdoro.usuario.application.repository.UsuarioRepository;
import dev.wakandaacademy.produdoro.usuario.domain.Usuario;

class UsuarioApplicationServiceTest2 {
	 @Mock
	    private UsuarioRepository usuarioRepository;

	    @InjectMocks
	    private UsuarioApplicationService usuarioApplicationService;

	    private UUID idUsuario;
	    private String usuarioEmail;
	    private Usuario usuario;

	    @BeforeEach
	    public void setup() {
	        MockitoAnnotations.openMocks(this);
	        idUsuario = UUID.randomUUID();
	        usuarioEmail = "test@example.com";
	        usuario = mock(Usuario.class);
	    }

	    @Test
	    public void testMudaStatusParaPausaCurta_Success() {
	        // Arrange
	        when(usuarioRepository.buscaUsuarioPorId(idUsuario)).thenReturn(usuario);
	        when(usuarioRepository.buscaUsuarioPorEmail(usuarioEmail)).thenReturn(usuario);

	        // Act
	        usuarioApplicationService.mudaStatusParaPausaCurta(idUsuario, usuarioEmail);

	        // Assert
	        verify(usuarioRepository).buscaUsuarioPorId(idUsuario);
	        verify(usuarioRepository).buscaUsuarioPorEmail(usuarioEmail);
	        verify(usuario).validaUsuario(idUsuario);
	        verify(usuario).mudaStatusPausaCurta();
	        verify(usuarioRepository).salva(usuario);
	    }

	    @Test
	    public void testMudaStatusParaPausaCurta_InvalidUser_ThrowsException() {
	        // Arrange
	        when(usuarioRepository.buscaUsuarioPorId(idUsuario)).thenReturn(usuario);
	        when(usuarioRepository.buscaUsuarioPorEmail(usuarioEmail)).thenReturn(usuario);
	        doThrow(APIException.build(HttpStatus.UNAUTHORIZED, "credencial de autenticação não é válida.")).when(usuario).validaUsuario(idUsuario);

	        // Act & Assert
	        APIException exception = assertThrows(APIException.class, () -> {
	            usuarioApplicationService.mudaStatusParaPausaCurta(idUsuario, usuarioEmail);
	        });

	        assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatusException());
	        assertEquals("credencial de autenticação não é válida.", exception.getMessage());

	        verify(usuarioRepository).buscaUsuarioPorId(idUsuario);
	        verify(usuarioRepository).buscaUsuarioPorEmail(usuarioEmail);
	        verify(usuario).validaUsuario(idUsuario);
	        verify(usuario, never()).mudaStatusPausaCurta();
	        verify(usuarioRepository, never()).salva(usuario);
	    }
	    
	    @Test
	    public void testMudaStatusParaPausaCurta_UserNotFound_ThrowsException() {
	        // Arrange
	        when(usuarioRepository.buscaUsuarioPorId(idUsuario)).thenThrow(APIException.build(HttpStatus.BAD_REQUEST, "Usuario não encontrado!"));

	        // Act & Assert
	        APIException exception = assertThrows(APIException.class, () -> {
	            usuarioApplicationService.mudaStatusParaPausaCurta(idUsuario, usuarioEmail);
	        });

	        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusException());
	        assertEquals("Usuario não encontrado!", exception.getMessage());

	        verify(usuarioRepository).buscaUsuarioPorId(idUsuario);
	        verify(usuarioRepository, never()).buscaUsuarioPorEmail(usuarioEmail);
	        verify(usuario, never()).validaUsuario(any(UUID.class));
	        verify(usuario, never()).mudaStatusPausaCurta();
	        verify(usuarioRepository, never()).salva(any(Usuario.class));
	    }
}
