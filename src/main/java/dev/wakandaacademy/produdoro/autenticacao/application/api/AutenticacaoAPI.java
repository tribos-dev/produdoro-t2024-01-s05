package dev.wakandaacademy.produdoro.autenticacao.application.api;

import javax.validation.Valid;

import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/public/v1/autenticacao")
public interface AutenticacaoAPI {
    @PostMapping
    TokenResponse autentica(@RequestBody @Valid AutenticacaoRequest autenticacaoRequest) throws AuthenticationException;
    @PostMapping("/reativacao")
    TokenResponse reativaAutenticacao(@RequestHeader("Authorization") String tokenExpirado) throws AuthenticationException;
}
