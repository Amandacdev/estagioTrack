package br.edu.ifpb.pweb2.estagiotrack.repository;

import br.edu.ifpb.pweb2.estagiotrack.model.Candidatura;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

// Falta implementar inscrição em ofertas
@Component
public class CandidaturaRepository {

    private Map<Integer, Candidatura> repositorio = new HashMap<>();

    // Busca uma candidatura pelo ID
    public Candidatura findById(Integer id) {
        return repositorio.get(id);
    }

    // Salva uma nova candidatura ou atualiza uma existente
    public void save(Candidatura candidatura) {
        Integer id = (candidatura.getId() == null) ? this.getMaxId() + 1 : candidatura.getId();
        candidatura.setId(id);
        repositorio.put(id, candidatura);
    }

    // Retorna todas as candidaturas
    public List<Candidatura> findAll() {
        return repositorio.values().stream().collect(Collectors.toList());
    }

    // Calcula o próximo ID disponível
    private Integer getMaxId() {
        return repositorio.keySet().stream().max(Integer::compareTo).orElse(0);
    }

    // Deleta uma candidatura pelo ID
    public void delete(Integer id) {
        repositorio.remove(id);
    }
}
