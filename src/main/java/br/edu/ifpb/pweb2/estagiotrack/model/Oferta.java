package br.edu.ifpb.pweb2.estagiotrack.model;

import br.edu.ifpb.pweb2.estagiotrack.model.enums.StatusOferta;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Oferta {

    @Id
    @GeneratedValue
    private Integer id;

    @Email
    private String emailOfertante;

    @ManyToOne
    private Empresa ofertante;

    @NotBlank(message = "Selecione uma área de atividade")
    private String atividadePrincipal;

    @NotBlank(message = "O Nome do cargo não pode ser vazio")
    private String tituloCargo;

    @NotBlank(message = "O valor da bolsa não pode ser vazio")
    private String valorBolsa;

    @NotBlank
    private String cargaHoraria;

    private String valeTransporte;

    private String requisitos;

    @ElementCollection
    private List<String> competencias;

    @Enumerated(EnumType.STRING)
    private StatusOferta statusOferta = StatusOferta.ABERTA;

    public void encerrar() {
        this.statusOferta = StatusOferta.ENCERRADA;
    }

    public StatusOferta getStatus() {
        return this.statusOferta;
    }
}
