package br.edu.ifpb.pweb2.estagiotrack.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifpb.pweb2.estagiotrack.model.CompetenciaTemplate;

@Repository
public interface CompetenciasTemplateRepository extends JpaRepository<CompetenciaTemplate, Integer> {

    List<CompetenciaTemplate> findAll();
}
