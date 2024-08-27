package br.edu.ifpb.pweb2.estagiotrack.model;

import br.edu.ifpb.pweb2.estagiotrack.model.enums.StatusCandidatura;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Candidatura{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
