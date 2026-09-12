package br.com.fiap3esph.autoescola3esph.service;

import br.com.fiap3esph.autoescola3esph.domain.aluno.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AlunoService {
    private final AlunoRepository repository;

    @Transactional
    public DadosDetalhamentoAluno cadastrarAluno(DadosCadastroAluno dados) {
        Aluno aluno = repository.save(new Aluno(dados));
        return new DadosDetalhamentoAluno(aluno);
    }

    @Transactional(readOnly = true)
    public Page<DadosListagemAluno> listarAlunos(Pageable paginacao) {
        return repository.findAllByAtivoTrue(paginacao).map(DadosListagemAluno::new);
    }

    @Transactional(readOnly = true)
    public DadosDetalhamentoAluno detalharAluno(Long id) {
        return new DadosDetalhamentoAluno(buscarAluno(id));
    }

    @Transactional
    public DadosDetalhamentoAluno atualizarAluno(DadosAtualizacaoAluno dados) {
        Aluno aluno = buscarAluno(dados.id());
        aluno.atualizarInformacoes(dados);
        return new DadosDetalhamentoAluno(aluno);
    }

    @Transactional
    public void excluirAluno(Long id) {
        buscarAluno(id).excluir();
    }

    private Aluno buscarAluno(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new AlunoNotFoundException("ID do aluno informado não existe!"));
    }
}
