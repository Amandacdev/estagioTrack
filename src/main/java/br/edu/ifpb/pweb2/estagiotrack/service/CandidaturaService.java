package br.edu.ifpb.pweb2.estagiotrack.service;

import br.edu.ifpb.pweb2.estagiotrack.model.Candidatura;
import br.edu.ifpb.pweb2.estagiotrack.repository.CandidaturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidaturaService {

    @Autowired
    private CandidaturaRepository candidaturaRepository;

    public List<Candidatura> findAll() {
        return candidaturaRepository.findAll();
    }

    public Candidatura save(Candidatura candidatura) {
        return candidaturaRepository.save(candidatura);
    }

    public Candidatura findById(Integer id) {
        return candidaturaRepository.findById(id).orElse(null);
    }

    public Candidatura findByEmail(String email) {
        return (Candidatura) candidaturaRepository.findByEmail(email);
        /*.orElse(null);*/
    }

}
