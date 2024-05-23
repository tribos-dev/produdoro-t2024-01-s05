package dev.wakandaacademy.produdoro.usuario.application.service;

import dev.wakandaacademy.produdoro.DataHelper;
import dev.wakandaacademy.produdoro.handler.APIException;
import dev.wakandaacademy.produdoro.usuario.application.repository.UsuarioRepository;
import dev.wakandaacademy.produdoro.usuario.domain.StatusUsuario;
import dev.wakandaacademy.produdoro.usuario.domain.Usuario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioApplicationServiceTest {

    @InjectMocks
    UsuarioApplicationService usuarioApplicationService;

    @Mock
    UsuarioRepository usuarioRepository;

    @Test
    void deveMudarOStatusParaPausaLonga(){
        // Dado
        Usuario usuario = DataHelper.createUsuario();
        // Quando
        when(usuarioRepository.buscaUsuarioPorEmail(anyString())).thenReturn(usuario);
        when(usuarioRepository.buscaUsuarioPorId(any())).thenReturn(usuario);
        usuarioApplicationService.mudaParaPausaLonga(usuario.getEmail(), usuario.getIdUsuario());
        // Entao
        assertEquals(StatusUsuario.PAUSA_LONGA, usuario.getStatus());
        verify(usuarioRepository, times(1)).salva(any());
    }
    @Test
    void naoDeveMudarOStatusParaPausaLonga(){
        // Dado
        Usuario usuario = DataHelper.createUsuario();
        // Quando
        when(usuarioRepository.buscaUsuarioPorEmail(anyString())).thenReturn(usuario);
        APIException e = assertThrows(APIException.class, () -> usuarioApplicationService.mudaParaPausaLonga(usuario.getEmail(), UUID.randomUUID()));
        // Entao
        assertEquals(APIException.class, e.getClass());
        assertEquals(HttpStatus.UNAUTHORIZED, e.getStatusException());
        assertEquals("credencial de autenticação não é válida.", e.getMessage());
    }

}