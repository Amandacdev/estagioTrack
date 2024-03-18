package br.edu.ifpb.pweb2.estagiotrack.repository;

import org.springframework.stereotype.Component;

import br.edu.ifpb.pweb2.estagiotrack.model.Aluno;

import java.util.*;
import java.util.stream.Collectors;


@Component
public class AlunoRepository {
    private Map<Integer,Aluno> repositorio = new HashMap<Integer, Aluno>();

    public Aluno findById(Integer id){ return repositorio.get(id);}

    public void save(Aluno aluno){
        Integer id = null;
        id = (aluno.getId() == null) ? this.getMaxId() + 1: aluno.getId();
        aluno.setId(id);
        repositorio.put(id,aluno);
    }

    public List<Aluno> findAll(){
        List<Aluno> alunos = repositorio.values().stream().collect(Collectors.toList());
        return alunos;
    }

    public Integer getMaxId(){
        List<Aluno> alunos = findAll();
        if(alunos == null || alunos.isEmpty())
          return 1;

       Aluno contaMaxId = alunos
               .stream()
               .max(Comparator.comparing(Aluno::getId))
               .orElseThrow(NoSuchElementException::new);
       return contaMaxId.getId() == null ? 1 : contaMaxId.getId() + 1;
    }
}
