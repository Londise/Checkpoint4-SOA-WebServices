package br.com.fiap3esph.autoescola3esph.service;

import br.com.fiap3esph.autoescola3esph.domain.agenda.ValidacaoException;
import br.com.fiap3esph.autoescola3esph.domain.usuario.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public DadosListagemUsuario cadastrarUsuario(DadosCadastroUsuario dados) {
        validarLoginDisponivel(dados.login(), null);
        Usuario usuario = new Usuario(dados.login(), passwordEncoder.encode(dados.senha()), dados.perfil());
        return new DadosListagemUsuario(repository.save(usuario));
    }

    @Transactional(readOnly = true)
    public Page<DadosListagemUsuario> listarUsuarios(Pageable paginacao) {
        return repository.findAll(paginacao).map(DadosListagemUsuario::new);
    }

    @Transactional(readOnly = true)
    public DadosListagemUsuario detalharUsuario(Long id) {
        return new DadosListagemUsuario(buscarUsuario(id));
    }

    @Transactional
    public DadosListagemUsuario atualizarUsuario(DadosAtualizacaoUsuario dados) {
        Usuario usuario = buscarUsuario(dados.id());
        validarLoginDisponivel(dados.login(), usuario.getId());
        usuario.atualizarPerfil(dados.login(), dados.perfil());
        return new DadosListagemUsuario(usuario);
    }

    @Transactional
    public void excluirUsuario(Long id) {
        repository.delete(buscarUsuario(id));
    }

    @Transactional
    public void alterarSenha(Long idUsuario, DadosAlteracaoSenha dados) {
        Usuario usuario = buscarUsuario(idUsuario);
        if (!passwordEncoder.matches(dados.senhaAtual(), usuario.getPassword())) {
            throw new ValidacaoException("A senha atual informada está incorreta!");
        }
        usuario.alterarSenha(passwordEncoder.encode(dados.novaSenha()));
    }

    private Usuario buscarUsuario(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException("ID do usuário informado não existe!"));
    }

    private void validarLoginDisponivel(String login, Long idUsuarioAtual) {
        repository.findByLogin(login).ifPresent(usuario -> {
            if (!usuario.getId().equals(idUsuarioAtual)) {
                throw new ValidacaoException("Já existe um usuário com este login!");
            }
        });
    }
}
