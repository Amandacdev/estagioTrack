package br.edu.ifpb.pweb2.estagiotrack.repository;

import br.edu.ifpb.pweb2.estagiotrack.model.Oferta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.edu.ifpb.pweb2.estagiotrack.model.Estagio;

import java.util.Optional;

@Repository
public interface EstagioRepository extends JpaRepository<Estagio, Integer>{
    @Query("SELECT COALESCE(MAX(e.id), 0) FROM Estagio e")
    Integer findMaxId();

    @Query("SELECT e FROM Estagio e " +
            "JOIN e.ofertaSelecionada o " +
            "JOIN o.ofertante ofertante " +
            "WHERE ofertante.email = :email")
    Page<Estagio> findEstagiosByOfertanteEmail(String email, Pageable pageable);
}
