package br.edu.ifpb.pweb2.estagiotrack.service;

import br.edu.ifpb.pweb2.estagiotrack.model.Aluno;
import br.edu.ifpb.pweb2.estagiotrack.model.Candidatura;
import br.edu.ifpb.pweb2.estagiotrack.repository.CandidaturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CandidaturaService {

    @Autowired
    private CandidaturaRepository candidaturaRepository;

    public Page<Candidatura> listAll(Pageable pageable) {
        return candidaturaRepository.findAll(pageable);
    }

    public List<Candidatura> findAll() {
        return candidaturaRepository.findAll();
    }

    public Candidatura save(Candidatura candidatura) {
        return candidaturaRepository.save(candidatura);
    }

    public Candidatura findById(Integer id) {
        return candidaturaRepository.findById(id).orElse(null);
    }

    public Optional<Candidatura> findByEmail(String email) {
        return candidaturaRepository.findByEmail(email);
    }

    public Integer findMaxId() {
        return candidaturaRepository.findMaxId();
    }
}
