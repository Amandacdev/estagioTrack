package br.edu.ifpb.pweb2.estagiotrack.model;

import br.edu.ifpb.pweb2.estagiotrack.model.enums.StatusCandidatura;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.io.Serializable;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Candidatura implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Email
    public String emailCandidato;

    @ManyToOne
    public Aluno alunoCandidato;

    @ManyToOne
    public Oferta ofertaSelecionada;

    @Enumerated(EnumType.STRING)
    public StatusCandidatura statusCandidatura;
}
