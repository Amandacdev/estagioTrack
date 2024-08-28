package br.edu.ifpb.pweb2.estagiotrack.service;

import br.edu.ifpb.pweb2.estagiotrack.model.CompetenciaTemplate;
import br.edu.ifpb.pweb2.estagiotrack.repository.CompetenciasTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompetenciasTemplateService {

    @Autowired
    private CompetenciasTemplateRepository competenciasTemplateRepository;

    public List<CompetenciaTemplate> findAll() {
        return competenciasTemplateRepository.findAll();
    }
}
