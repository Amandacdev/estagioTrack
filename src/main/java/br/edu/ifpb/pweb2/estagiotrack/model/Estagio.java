package br.edu.ifpb.pweb2.estagiotrack.model;

import br.edu.ifpb.pweb2.estagiotrack.model.enums.StatusOferta;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@AllArgsConstructor
public class Estagio implements Serializable {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    private Aluno alunoAprovado;

    @ManyToOne //Deveria ser 1:1 já que no nosso caso nossas ofertas não têm quantidade de vagas, então cada uma só gera 1 estágio.
    private Oferta ofertaSelecionada;

    public Estagio(){
        encerrarOferta();
    }

    public void encerrarOferta(){
        if (ofertaSelecionada != null && ofertaSelecionada.getStatus() == StatusOferta.ABERTA) {
            ofertaSelecionada.encerrar();
        }
    }
}
