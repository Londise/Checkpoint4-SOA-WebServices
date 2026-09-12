package br.com.fiap3esph.autoescola3esph.domain.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DadosAlteracaoSenha(
        @NotBlank String senhaAtual,
        @NotBlank @Size(min = 8, max = 100) String novaSenha) {
}
