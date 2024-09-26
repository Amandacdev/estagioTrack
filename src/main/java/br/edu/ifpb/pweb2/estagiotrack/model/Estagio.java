package br.edu.ifpb.pweb2.estagiotrack.model;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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

    @OneToOne
    @NotBlank(message = "O campo Estudante é obrigatório.")
    private Aluno alunoAprovado;

    @OneToOne
    @NotBlank(message = "O campo Oferta é obrigatório.")
    private Oferta ofertaSelecionada;

    @ManyToOne
    @NotBlank(message = "O campo Candidatura é obrigatório.")
    private Candidatura candidaturaSelecionada;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotBlank(message = "O campo Data de início é obrigatório.")
    private LocalDate dataInicio;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotBlank(message = "O campo Data de término é obrigatório.")
    private LocalDate dataFim;

    private String observacoes;
}
