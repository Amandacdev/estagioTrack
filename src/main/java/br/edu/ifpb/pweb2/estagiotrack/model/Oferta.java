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
    public Integer id;

    @ManyToOne
    public Empresa ofertante;

    @Email
    public String emailOfertante;

    @NotBlank(message = "O Nome do cargo não pode ser vazio")
    public String tituloCargo;

    @NotBlank(message = "O valor da bolsa não pode ser vazio")
    public String valorBolsa;

    @NotBlank
    public String turno;

    @Enumerated(EnumType.STRING)
    private StatusOferta statusOferta;

    public void encerrar() {
        this.statusOferta = StatusOferta.ENCERRADA;
    }

    public StatusOferta getStatus() {
        return this.statusOferta;
    }
}
