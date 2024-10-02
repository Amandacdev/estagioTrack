package br.edu.ifpb.pweb2.estagiotrack.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.edu.ifpb.pweb2.estagiotrack.model.Oferta;
import br.edu.ifpb.pweb2.estagiotrack.model.enums.StatusOferta;

@Repository
public interface OfertaRepository extends JpaRepository<Oferta, Integer> {

    @Query("SELECT o FROM Oferta o JOIN o.competencias c WHERE c IN :competencias GROUP BY o HAVING COUNT(c) > 0")
    Page<Oferta> findByCompetencias(List<String> competencias, Pageable pageable);

    Page<Oferta> findByStatusOferta(StatusOferta status, Pageable pageable);

    @Query("SELECT COALESCE(MAX(o.id), 0) FROM Oferta o")
    Integer findMaxId();

    Page<Oferta> findByEmailOfertante(String emailOfertante, Pageable pageable);
}
