package br.edu.ifpb.pweb2.estagiotrack.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Data
public class Candidatura implements Serializable {

    @Setter
    @Getter
    public Integer id;
    public Integer idOferta;
    public String emailCandidato;
    public Aluno alunoCandidato;
    public Oferta ofertaSelecionada;

    public Candidatura(Integer idOferta, String emailCandidato, Aluno alunoCandidato, Oferta ofertaSelecionada) {
        this.idOferta = idOferta;
        this.emailCandidato = emailCandidato;
        this.alunoCandidato = alunoCandidato;
        this.ofertaSelecionada = ofertaSelecionada;
    }

    // public Candidatura(Integer id, Aluno alunoCandidato, Oferta
    // ofertaSelecionada) {
    // this.id = id;
    // this.alunoCandidato = alunoCandidato;
    // this.ofertaSelecionada = ofertaSelecionada;
    // }
}
