package br.edu.ifpb.pweb2.estagiotrack.repository;

import br.edu.ifpb.pweb2.estagiotrack.model.Candidatura;
import br.edu.ifpb.pweb2.estagiotrack.model.Oferta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CandidaturaRepository extends JpaRepository<Candidatura, Integer> {

    @Query("SELECT c FROM Candidatura c WHERE c.emailCandidato = :email")
    Optional<Candidatura> findByEmail(@Param("email") String email);

    @Query("SELECT COALESCE(MAX(c.id), 0) FROM Candidatura c")
    Integer findMaxId();

    List<Candidatura> findByOfertaSelecionada(Oferta oferta);

}
