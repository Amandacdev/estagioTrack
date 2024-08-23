package br.edu.ifpb.pweb2.estagiotrack.repository;

import br.edu.ifpb.pweb2.estagiotrack.model.Oferta;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OfertaRepository extends JpaRepository<Oferta, Integer> {

    @Query("SELECT COALESCE(MAX(o.id), 0) FROM Oferta o")
    Integer getMaxId();

    @Query("SELECT o FROM Oferta o JOIN o.competencias c WHERE c IN :competencias GROUP BY o HAVING COUNT(c) > 0")
    List<Oferta> findByCompetencias(List<String> competencias);
}
