package br.com.fiap3esph.autoescola3esph.domain.aluno;

import br.com.fiap3esph.autoescola3esph.domain.endereco.DadosEndereco;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoAluno(
        @NotNull Long id,
        String nome,
        String telefone,
        @Valid DadosEndereco endereco) {
}
