package br.com.fiap3esph.autoescola3esph.domain.agenda.validacao;

import br.com.fiap3esph.autoescola3esph.domain.agenda.DadosAgendamento;
import br.com.fiap3esph.autoescola3esph.domain.agenda.InstrucaoRepository;
import br.com.fiap3esph.autoescola3esph.domain.agenda.ValidacaoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ValidadorLimiteDiarioAluno implements ValidadorAgendamento {
    private final InstrucaoRepository repository;

    @Override
    public void validar(DadosAgendamento dados) {
        LocalDateTime inicioDia = dados.dataHora().toLocalDate().atStartOfDay();
        LocalDateTime inicioProximoDia = inicioDia.plusDays(1);

        long quantidadeInstrucoesNoDia = repository
                .countByAlunoIdAndDataHoraGreaterThanEqualAndDataHoraLessThanAndCanceladoFalse(
                dados.idAluno(),
                inicioDia,
                inicioProximoDia
        );

        if (quantidadeInstrucoesNoDia >= 2) {
            throw new ValidacaoException("O aluno já possui duas instruções agendadas nesta data!");
        }
    }
}
