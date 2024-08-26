package br.edu.ifpb.pweb2.estagiotrack.repository;

import br.edu.ifpb.pweb2.estagiotrack.model.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Integer> {
    Optional<Empresa> findByEmail(String email);

    Optional<Empresa> findByRazaoSocial(String razaoSocial);
}
