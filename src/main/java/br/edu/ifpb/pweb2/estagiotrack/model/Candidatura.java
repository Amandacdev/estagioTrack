package br.edu.ifpb.pweb2.estagiotrack.model;

import br.edu.ifpb.pweb2.estagiotrack.model.enums.StatusCandidatura;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Candidatura {

    @Id
    private Integer id;

    @Email
    private String emailCandidato;

    @ManyToOne
    private Aluno alunoCandidato;

    @ManyToOne
    private Oferta ofertaSelecionada;

    @Enumerated(EnumType.STRING)
    private StatusCandidatura statusCandidatura = StatusCandidatura.PENDENTE;

    public StatusCandidatura getStatus() {
        return this.statusCandidatura;
    }
}
