package br.edu.ifpb.pweb2.estagiotrack.repository;

import org.springframework.stereotype.Component;

import br.edu.ifpb.pweb2.estagiotrack.model.Empresa;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class EmpresaRepository {
    private Map<Integer, Empresa> repositorio = new HashMap<Integer, Empresa>();

    public Empresa findById(Integer id) {
        return repositorio.get(id);
    }

    // Parte do workaround para vincular oferta a empresa dinamicamente.
    public Empresa findByEmail(String email) {
        for (Empresa empresa : repositorio.values()) {
            if (empresa.getEmail().equals(email)) {
                System.out.println("Empresa encontrada" + empresa + email);
                return empresa;
            }
        }
        return null;
    }

    public void save(Empresa empresa) {
        Integer id = null;
        id = (empresa.getId() == null) ? this.getMaxId() + 1 : empresa.getId();
        empresa.setId(id);
        repositorio.put(id, empresa);
    }

    public List<Empresa> findAll() {
        List<Empresa> empresas = repositorio.values().stream().collect(Collectors.toList());
        return empresas;
    }

    public Integer getMaxId() {
        List<Empresa> empresas = findAll();
        if (empresas == null || empresas.isEmpty())
            return 1;

        Empresa contaMaxId = empresas
                .stream()
                .max(Comparator.comparing(Empresa::getId))
                .orElseThrow(NoSuchElementException::new);
        return contaMaxId.getId() == null ? 1 : contaMaxId.getId() + 1;
    }
}
