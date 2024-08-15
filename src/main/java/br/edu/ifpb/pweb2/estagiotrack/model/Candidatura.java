package br.edu.ifpb.pweb2.estagiotrack.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Candidatura implements Serializable {

    private Integer id; 

    private Aluno alunoCandidato;
    private Oferta ofertaSelecionada;

    public Candidatura(Aluno alunoCandidato, Oferta ofertaSelecionada) {
        this.alunoCandidato = alunoCandidato;
        this.ofertaSelecionada = ofertaSelecionada;
    }

    public Candidatura(Integer id, Aluno alunoCandidato, Oferta ofertaSelecionada) {
        this.id = id;
        this.alunoCandidato = alunoCandidato;
        this.ofertaSelecionada = ofertaSelecionada;
    }
}
