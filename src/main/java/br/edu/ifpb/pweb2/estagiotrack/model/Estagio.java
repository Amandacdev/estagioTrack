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

    @ManyToOne
    private Oferta ofertaSelecionada;

    public Estagio(){
        encerrarOferta();
    }

    private void encerrarOferta(){
        if (ofertaSelecionada != null && ofertaSelecionada.getStatus() == StatusOferta.ABERTA) {
            ofertaSelecionada.encerrar();
        }
    }
}
