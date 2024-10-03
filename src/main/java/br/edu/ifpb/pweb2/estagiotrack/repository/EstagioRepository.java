package br.edu.ifpb.pweb2.estagiotrack.repository;


import br.edu.ifpb.pweb2.estagiotrack.model.ResultadoEstagio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.edu.ifpb.pweb2.estagiotrack.model.Estagio;

@Repository
public interface EstagioRepository extends JpaRepository<Estagio, Integer>{
    @Query("SELECT COALESCE(MAX(e.id), 0) FROM Estagio e")
    Integer findMaxId();

    @Query("SELECT e FROM Estagio e " +
            "JOIN e.ofertaSelecionada o " +
            "JOIN o.ofertante ofertante " +
            "WHERE ofertante.email = :email")
    Page<Estagio> findEstagiosByOfertanteEmail(String email, Pageable pageable);

    @Query("SELECT new br.edu.ifpb.pweb2.estagiotrack.model.ResultadoEstagio(a.nome, e.razaoSocial, e.cnpj) " +
            "FROM Estagio est " +
            "JOIN est.alunoAprovado a " +
            "JOIN est.ofertaSelecionada o " +
            "JOIN o.ofertante e " +
            "WHERE est.id = :estagioId")
    ResultadoEstagio findDadosEstagioPorId(@Param("estagioId") Integer estagioId);
}
