package br.edu.ifpb.pweb2.estagiotrack.repository;

import br.edu.ifpb.pweb2.estagiotrack.model.Candidatura;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

// Falta implementar inscrição em ofertas
@Component
public class CandidaturaRepository {

    private Map<Integer, Candidatura> repositorio = new HashMap<Integer, Candidatura>();

    public Candidatura findById(Integer id) {
        return repositorio.get(id);
    }

    public void save(Candidatura candidatura) {
        Integer id = (candidatura.getId() == null) ? this.getMaxId() + 1 : candidatura.getId();
        candidatura.setId(id);
        repositorio.put(id, candidatura);
    }

    public List<Candidatura> findAll() {
        return repositorio.values().stream().collect(Collectors.toList());
    }

    private Integer getMaxId() {
        return repositorio.keySet().stream().max(Integer::compareTo).orElse(0);
    }

    public void delete(Integer id) {
        repositorio.remove(id);
    }
}
