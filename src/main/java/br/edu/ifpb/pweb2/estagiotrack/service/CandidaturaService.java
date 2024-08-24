package br.edu.ifpb.pweb2.estagiotrack.service;

import br.edu.ifpb.pweb2.estagiotrack.model.Candidatura;
import br.edu.ifpb.pweb2.estagiotrack.model.Oferta;
import br.edu.ifpb.pweb2.estagiotrack.repository.CandidaturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        return candidaturaRepository.findByEmail(email).orElse(null);
    }

}



