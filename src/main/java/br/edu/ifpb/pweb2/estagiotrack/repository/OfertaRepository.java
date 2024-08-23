package br.edu.ifpb.pweb2.estagiotrack.repository;

import br.edu.ifpb.pweb2.estagiotrack.model.Oferta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OfertaRepository extends JpaRepository<Oferta, Integer> {

    //Consulta o Id maximo
    @Query("SELECT COALESCE(MAX(o.id), 0) FROM Oferta o")
    Integer getMaxId();
}
