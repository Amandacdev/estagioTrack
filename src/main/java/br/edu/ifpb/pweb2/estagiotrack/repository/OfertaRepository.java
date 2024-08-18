package br.edu.ifpb.pweb2.estagiotrack.repository;

import org.springframework.stereotype.Component;

import br.edu.ifpb.pweb2.estagiotrack.model.Oferta;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class OfertaRepository {
    private Map<Integer, Oferta> repositorio = new HashMap<Integer, Oferta>();

    public Oferta findById(Integer id) {
        return repositorio.get(id);
    }

    public void save(Oferta oferta) {
        Integer id = null;
        id = (oferta.getId() == null) ? this.getMaxId() + 1 : oferta.getId();
        oferta.setId(id);
        repositorio.put(id, oferta);
    }

    public List<Oferta> findAll() {
        List<Oferta> ofertas = repositorio.values().stream().collect(Collectors.toList());
        return ofertas;
    }

    public Integer getMaxId() {
        List<Oferta> ofertas = findAll();
        if (ofertas == null || ofertas.isEmpty())
            return 1;

        Oferta contaMaxId = ofertas
                .stream()
                .max(Comparator.comparing(Oferta::getId))
                .orElseThrow(NoSuchElementException::new);
        return contaMaxId.getId() == null ? 1 : contaMaxId.getId() + 1;
    }

    public void delete(Integer id) {
        repositorio.remove(id);
    }
}
