package br.edu.ifpb.pweb2.estagiotrack.repository;

import br.edu.ifpb.pweb2.estagiotrack.model.Aluno;
import br.edu.ifpb.pweb2.estagiotrack.model.Usuario;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;


@Component
public class UsuarioRepository {
    private Map<Integer,Usuario> repositorio = new HashMap<Integer, Usuario>();

    public Usuario findById(Integer id){ return repositorio.get(id);}

    public void save(Usuario usuario){
        Integer id = null;
        id = (usuario.getId() == null) ? this.getMaxId() + 1: usuario.getId();
        usuario.setId(id);
        repositorio.put(id,usuario);
    }

    public List<Usuario> findAll(){
        List<Usuario> usuarios = repositorio.values().stream().collect(Collectors.toList());
        return usuarios;
    }

    public Integer getMaxId(){
        List<Usuario> usuarios = findAll();
        if(usuarios == null || usuarios.isEmpty())
          return 1;

       Usuario contaMaxId = usuarios
               .stream()
               .max(Comparator.comparing(Usuario::getId))
               .orElseThrow(NoSuchElementException::new);
       return contaMaxId.getId() == null ? 1 : contaMaxId.getId() + 1;
    }
}
