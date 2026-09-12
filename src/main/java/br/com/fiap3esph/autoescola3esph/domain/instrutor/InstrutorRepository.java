package br.com.fiap3esph.autoescola3esph.domain.instrutor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface InstrutorRepository extends JpaRepository<Instrutor, Long> {
    Page<Instrutor> findAllByAtivoTrue(Pageable paginacao);

    @Query(value = """
        select i.* from instrutores i
        where i.ativo = true
          and i.especialidade = :especialidade
          and not exists (
              select 1 from instrucoes a
              where a.instrutor_id = i.id
                and a.data_hora = :dataHora
                and a.cancelado = false
          )
        order by rand()
        limit 1
    """, nativeQuery = true)
    Instrutor escolherInstrutorAleatorioDisponivel(
            @Param("especialidade") Especialidade especialidade,
            @Param("dataHora") LocalDateTime dataHora);

    boolean existsByIdAndAtivoFalse(Long id);
}
