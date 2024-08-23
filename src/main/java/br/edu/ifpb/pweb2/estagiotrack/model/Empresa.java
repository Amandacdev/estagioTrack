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

        @NotBlank /*
                   * @Pattern(
                   * regexp = "\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}|\\d{14}",
                   * message = "CNPJ deve estar no formato 99.999.999/9999-99"
                   * )
                   */
        private String cnpj;

        @NotBlank
        private String cep;

        @NotBlank
        private String logradouro;

        private String numeroImovel;

        @NotBlank
        private String bairro;

        @NotBlank
        private String cidade;

        @NotBlank
        private String estado;

        @NotBlank
        /*
         * @Pattern(
         * regexp = "\\(\\d{2}\\) \\d{5}-\\d{4}|\\d{5}-\\d{4}|\\d{10}",
         * message = "Número de celular deve estar no formato (99) 99999-9999"
         * )
         */
        private String telefone;

        @NotBlank
        private String atividadePrincipal;
        private String site;
        private byte[] comprovanteEndereco;

        @NotBlank
        private String razaoSocial;

        @NotBlank
        private String responsavel;

        @NotBlank
        @Email
        private String email;

        @NotBlank
        private String senha;

}
