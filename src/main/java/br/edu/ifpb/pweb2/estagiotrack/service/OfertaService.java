package br.edu.ifpb.pweb2.estagiotrack.service;

import br.edu.ifpb.pweb2.estagiotrack.model.enums.StatusOferta;
import br.edu.ifpb.pweb2.estagiotrack.model.Oferta;
import br.edu.ifpb.pweb2.estagiotrack.repository.OfertaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class OfertaService {

    @Autowired
    private OfertaRepository ofertaRepository;

    public Page<Oferta> listAll(Pageable pageable) {
        return ofertaRepository.findAll(pageable);
    }

    public Page<Oferta> findByCompetencias(String competencia, Pageable pageable) {
        return ofertaRepository.findByCompetencias(Collections.singletonList(competencia), pageable);
    }

    public List<Oferta> findAll() {
        return ofertaRepository.findAll();
    }

    public Optional<Oferta> findById(Integer id) {
        return ofertaRepository.findById(id);
    }

    public Page<Oferta> findByEmailOfertante(String emailOfertante, Pageable pageable) { return ofertaRepository.findByEmailOfertante(emailOfertante, pageable); }

    public Oferta save(Oferta oferta) {
        return ofertaRepository.save(oferta);
    }

    public List<Oferta> listarOfertasAbertas() {
        return ofertaRepository.findByStatusOferta(StatusOferta.ABERTA);
    }

    public Integer findMaxId() {
        return ofertaRepository.findMaxId();
    }
}
