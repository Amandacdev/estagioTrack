package br.edu.ifpb.pweb2.estagiotrack.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Data
public class Empresa implements Serializable {

    @Setter
    @Getter
    public Integer id;
    public String razaoSocial;
    public String responsavel;
    public String email;
    public String senha;

    public Empresa(Integer id, String razaoSocial, String responsavel, String email, String senha) {
        this.id = id;
        this.razaoSocial = razaoSocial;
        this.responsavel = responsavel;
        this.email = email;
        this.senha = senha;
    }
}
