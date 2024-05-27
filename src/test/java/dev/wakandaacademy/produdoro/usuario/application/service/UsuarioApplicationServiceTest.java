package dev.wakandaacademy.produdoro.usuario.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import dev.wakandaacademy.produdoro.DataHelper;
import dev.wakandaacademy.produdoro.handler.APIException;
import dev.wakandaacademy.produdoro.usuario.application.repository.UsuarioRepository;
import dev.wakandaacademy.produdoro.usuario.domain.StatusUsuario;
import dev.wakandaacademy.produdoro.usuario.domain.Usuario;
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
    void sucessoAlteraStatusParaPausaCurta() {    
        Usuario usuario = DataHelper.createUsuario();
        when(usuarioRepository.buscaUsuarioPorEmail(anyString())).thenReturn(usuario);
        when(usuarioRepository.buscaUsuarioPorId(any(UUID.class))).thenReturn(usuario);
        usuarioApplicationService.mudaStatusParaPausaCurta(usuario.getIdUsuario(), usuario.getEmail());
        verify(usuarioRepository, times(1)).salva(usuario);
        assertEquals(StatusUsuario.PAUSA_CURTA, usuario.getStatus());
    }

    @Test
    void usuarioInvalidoNaoMudarStatusParaPausaCurta() {       
        Usuario usuario = DataHelper.createUsuario();
        UUID idUsuarioInvalido = UUID.fromString("b92ee6fa-9ae9-45ac-afe0-fb8e4460d839");
        when(usuarioRepository.buscaUsuarioPorEmail(anyString())).thenReturn(usuario);
       
        APIException e = assertThrows(APIException.class,
                () -> usuarioApplicationService.mudaStatusParaPausaCurta(idUsuarioInvalido, usuario.getEmail()));        
        assertEquals(HttpStatus.UNAUTHORIZED, e.getStatusException());
    }

    @Test
    void deveMudarStatusParaFoco(){
        //Dado
        Usuario usuario = DataHelper.createUsuario();
        //Quando
        when(usuarioRepository.buscaUsuarioPorEmail(anyString())).thenReturn(usuario);
        when(usuarioRepository.buscaUsuarioPorId(any())).thenReturn(usuario);
        usuarioApplicationService.mudaStatusParaFoco(usuario.getEmail(),usuario.getIdUsuario());
        //Entao
        assertEquals(StatusUsuario.FOCO,usuario.getStatus());
        verify(usuarioRepository,times(1)).buscaUsuarioPorId(usuario.getIdUsuario());
        verify(usuarioRepository,times(1)).buscaUsuarioPorEmail(usuario.getEmail());
        verify(usuarioRepository,times(1)).salva(usuario);
    }
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
        assertEquals("Credencial de autenticação não é válida", e.getMessage());
    }
}


