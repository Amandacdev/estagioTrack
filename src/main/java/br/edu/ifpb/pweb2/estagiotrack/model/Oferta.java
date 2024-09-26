package br.edu.ifpb.pweb2.estagiotrack.model;

import java.util.List;

import br.edu.ifpb.pweb2.estagiotrack.model.enums.StatusOferta;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Oferta {

    @Id
    private Integer id;

    @Email(message = "O e-mail do ofertante deve estar no formato correto.")
    private String emailOfertante;

    @ManyToOne
    private Empresa ofertante;

    @NotBlank(message = "A área de atividade é obrigatória.")
    private String atividadePrincipal;

    @NotBlank(message = "O nome do cargo é obrigatório.")
    private String tituloCargo;

    @NotBlank(message = "O valor da bolsa é obrigatório.")
    private String valorBolsa;

    @NotBlank(message = "A carga horária é obrigatória.")
    private String cargaHoraria;

    private String valeTransporte;

    private String requisitos;

    @ElementCollection
    @NotEmpty(message = "Pelo menos uma competência deve ser selecionada.")
    private List<String> competencias;

    @Enumerated(EnumType.STRING)
    public StatusOferta statusOferta = StatusOferta.ABERTA;

    public StatusOferta getStatus() {
        return this.statusOferta;
    }
}
