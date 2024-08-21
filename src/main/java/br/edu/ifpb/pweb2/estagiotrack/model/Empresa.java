package br.edu.ifpb.pweb2.estagiotrack.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.io.Serializable;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Empresa implements Serializable {

    @Setter
    @Getter
    @Id
    @GeneratedValue
    private Integer id;

    @NotBlank/*
    @Pattern(
            regexp = "\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}|\\d{14}",
            message = "CNPJ deve estar no formato 99.999.999/9999-99"
    )*/
    public String cnpj;

    @NotBlank
    public String cep;

    @NotBlank
    public String logradouro;

    public String numeroImovel;

    @NotBlank
    public String bairro;

    @NotBlank
    public String cidade;

    @NotBlank
    public String estado;

    @NotBlank
    /*@Pattern(
            regexp = "\\(\\d{2}\\) \\d{5}-\\d{4}|\\d{5}-\\d{4}|\\d{10}",
            message = "Número de celular deve estar no formato (99) 99999-9999"
    )*/
    public String telefone;

    public String atividadePrincipal;
    public String site;
    public byte[] comprovanteEndereco;

    @NotBlank
    public String razaoSocial;

    @NotBlank
    public String responsavel;

    @NotBlank
    @Email
    public String email;

    @NotBlank
    public String senha;

}
