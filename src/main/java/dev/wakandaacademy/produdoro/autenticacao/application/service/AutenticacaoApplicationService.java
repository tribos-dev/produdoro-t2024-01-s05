package dev.wakandaacademy.produdoro.autenticacao.application.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import dev.wakandaacademy.produdoro.autenticacao.domain.Token;

public interface AutenticacaoApplicationService {
    Token autentica(UsernamePasswordAuthenticationToken userCredentials);
    Token reativaToken(String tokenExpirado);
}
