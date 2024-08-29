package br.edu.ifpb.pweb2.estagiotrack.repository;

import br.edu.ifpb.pweb2.estagiotrack.model.CompetenciaTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompetenciasTemplateRepository extends JpaRepository<CompetenciaTemplate, String> {

    List<CompetenciaTemplate> findAll();
}
