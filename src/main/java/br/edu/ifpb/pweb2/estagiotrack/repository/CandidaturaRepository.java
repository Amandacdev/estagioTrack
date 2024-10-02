package br.edu.ifpb.pweb2.estagiotrack.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.edu.ifpb.pweb2.estagiotrack.model.Candidatura;
import br.edu.ifpb.pweb2.estagiotrack.model.Oferta;

@Repository
public interface CandidaturaRepository extends JpaRepository<Candidatura, Integer> {

    @Query("SELECT c FROM Candidatura c WHERE c.emailCandidato = :email")
    Optional<Candidatura> findByEmail(@Param("email") String email);

    @Query("SELECT COALESCE(MAX(c.id), 0) FROM Candidatura c")
    Integer findMaxId();

    @Query("SELECT c FROM Candidatura c WHERE c.emailCandidato = :email")
    Page<Candidatura> findByEmailCandidato(String email, Pageable pageable);

    List<Candidatura> findByOfertaSelecionada(Oferta oferta);

}
