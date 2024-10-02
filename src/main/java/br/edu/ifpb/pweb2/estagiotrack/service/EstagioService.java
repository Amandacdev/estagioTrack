package br.edu.ifpb.pweb2.estagiotrack.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.edu.ifpb.pweb2.estagiotrack.model.Estagio;
import br.edu.ifpb.pweb2.estagiotrack.repository.EstagioRepository;

@Service
public class EstagioService {
    
    @Autowired
    private EstagioRepository estagioRepository;

    public Page<Estagio> listAll(Pageable pageable) { return estagioRepository.findAll(pageable); }

    public Page<Estagio> buscarEstagiosPorEmailOfertante(String email, Pageable pageable) {
        return estagioRepository.findEstagiosByOfertanteEmail(email, pageable);
    }

    public List<Estagio> findAll() {
        return estagioRepository.findAll();
    }

    public Optional<Estagio> findById(Integer id) {
        return estagioRepository.findById(id);
    }

    public Estagio save(Estagio estagio) {
        return estagioRepository.save(estagio);
    }

    public Integer findMaxId() {
        return estagioRepository.findMaxId();
    }
}