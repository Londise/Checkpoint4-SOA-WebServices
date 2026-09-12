package br.com.fiap3esph.autoescola3esph.domain.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoUsuario(
        @NotNull Long id,
        @NotBlank String login,
        @NotNull Role perfil) {
}
