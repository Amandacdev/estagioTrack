package br.edu.ifpb.pweb2.estagiotrack.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.edu.ifpb.pweb2.estagiotrack.model.Empresa;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Integer> {

    Optional<Empresa> findByEmail(@Param("email") String email);

    Optional<Empresa> findByCnpj(String cnpj);

    boolean existsByEmail(String email);

    boolean existsByCnpj(String cnpj);

    @Query("SELECT COALESCE(MAX(e.id), 0) FROM Empresa e")
    Integer findMaxId();
}
