package br.edu.ifpb.pweb2.estagiotrack.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class CompetenciaTemplate {

    @Id
    private Integer id;

    private String nome;

    private String iconeUrl;
}
