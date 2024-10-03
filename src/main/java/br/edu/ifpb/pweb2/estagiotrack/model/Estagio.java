package br.edu.ifpb.pweb2.estagiotrack.model;

import java.time.LocalDate;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Estagio {

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "aluno_aprovado_id")
    private Aluno alunoAprovado;

    @ManyToOne
    @JoinColumn(name = "oferta_selecionada_id")
    private Oferta ofertaSelecionada;

    @ManyToOne
    private Candidatura candidaturaSelecionada;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataInicio;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataFim;

    private String observacoes;
}
