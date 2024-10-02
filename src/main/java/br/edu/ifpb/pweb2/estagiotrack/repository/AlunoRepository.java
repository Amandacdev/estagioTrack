package br.edu.ifpb.pweb2.estagiotrack.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.edu.ifpb.pweb2.estagiotrack.model.Aluno;

import java.util.Optional;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Integer> {

    Optional<Aluno> findByNomeUsuario(@Param("nomeUsuario") String nomeUsuario);

    Optional<Aluno> findByEmail(String email);

    @Query("SELECT COALESCE(MAX(a.id), 0) FROM Aluno a")
    Integer findMaxId();
}
