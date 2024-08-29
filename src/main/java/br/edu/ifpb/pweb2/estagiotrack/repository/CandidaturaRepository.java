package br.edu.ifpb.pweb2.estagiotrack.repository;

import br.edu.ifpb.pweb2.estagiotrack.model.Candidatura;
import br.edu.ifpb.pweb2.estagiotrack.model.Oferta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidaturaRepository extends JpaRepository<Candidatura, Integer> {

    // Consulta para encontrar a candidatura por email do candidato
    @Query("SELECT c FROM Candidatura c WHERE c.emailCandidato = :email")
    List<Candidatura> findByEmail(@Param("email") String email);

    // Consulta para obter o ID máximo
    @Query("SELECT COALESCE(MAX(c.id), 0) FROM Candidatura c")
    Integer findMaxId();

    List<Candidatura> findByOfertaSelecionada(Oferta oferta);

}
