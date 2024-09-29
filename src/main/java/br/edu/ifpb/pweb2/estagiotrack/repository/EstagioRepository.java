package br.edu.ifpb.pweb2.estagiotrack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.edu.ifpb.pweb2.estagiotrack.model.Estagio;

@Repository
public interface EstagioRepository extends JpaRepository<Estagio, Integer>{
    
    @Query("SELECT COALESCE(MAX(e.id), 0) FROM Estagio e")
    Integer findMaxId();
}
