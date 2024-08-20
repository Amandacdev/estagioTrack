package br.edu.ifpb.pweb2.estagiotrack.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Data
public class Aluno implements Serializable {

    @Setter
    @Getter
    public Integer id;
    public String email;
    public String nomeUsuario;
    public String senha;
    public String nome;
    public String genero;
    public List<String> competencias;

    public Aluno(Integer id, String email, String nomeUsuario, String senha, String nome, String genero, List<String> competencias) {
        this.id = id;
        this.email = email;
        this.nomeUsuario = nomeUsuario;
        this.senha = senha;
        this.nome = nome;
        this.genero = genero;
        this.competencias = competencias;
    }
}
