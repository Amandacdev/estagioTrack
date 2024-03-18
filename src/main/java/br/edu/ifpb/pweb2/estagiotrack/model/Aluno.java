package br.edu.ifpb.pweb2.estagiotrack.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Data
public class Aluno implements Serializable {

    @Setter
    @Getter
    public Integer id;
    public String nome;
    public String senha;
    public Aluno(Integer id, String nome, String senha){
        this.id = id;
        this.nome = nome;
        this.senha = senha;
    }

}
