package br.edu.ifpb.pweb2.estagiotrack.model;

import br.edu.ifpb.pweb2.estagiotrack.model.enums.StatusOferta;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Oferta implements Serializable {

    @Setter
    @Getter
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    private Empresa ofertante;

    @Email
    private String emailOfertante;

    @NotBlank(message = "O Nome do cargo não pode ser vazio")
    private String tituloCargo;

    @NotBlank(message = "O valor da bolsa não pode ser vazio")
    private String valorBolsa;

    @NotBlank
    private String turno;

    @Enumerated(EnumType.STRING)
    private StatusOferta statusOferta;

    public void encerrar() {
        this.statusOferta = StatusOferta.ENCERRADA;
    }

    public StatusOferta getStatus() {
        return this.statusOferta;
    }
}
