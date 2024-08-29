package br.edu.ifpb.pweb2.estagiotrack.model;

import br.edu.ifpb.pweb2.estagiotrack.model.enums.StatusOferta;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity
@AllArgsConstructor
public class Estagio {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    private Aluno alunoAprovado;

    @OneToOne
    private Oferta ofertaSelecionada;

    public Estagio() {
        encerrarOferta();
    }

    public void encerrarOferta() {
        if (ofertaSelecionada != null && ofertaSelecionada.getStatus() == StatusOferta.ABERTA) {
            ofertaSelecionada.encerrar();
        }
    }
}
