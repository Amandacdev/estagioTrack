package br.edu.ifpb.pweb2.estagiotrack.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Empresa implements Serializable {

        @Id
        private Integer id;
        @NotBlank(message = "O CNPJ é obrigatório.")
        @Column(unique = true, nullable = false)
        private String cnpj;

        @NotBlank
        @Column(unique = true, nullable = false)
        private String nomeUsuario;

        @NotBlank(message = "O CEP é obrigatório.")
        private String cep;

        @NotBlank(message = "O logradouro é obrigatório.")
        private String logradouro;

        private String numeroImovel;

        @NotBlank(message = "O bairro é obrigatório.")
        private String bairro;

        @NotBlank(message = "A cidade é obrigatória.")
        private String cidade;

        @NotBlank(message = "O estado é obrigatório.")
        private String estado;

        @NotBlank(message = "O telefone é obrigatório.")
        private String telefone;

        @NotBlank(message = "A atividade principal é obrigatória.")
        private String atividadePrincipal;

        private String site;

        @Column(name = "comprovante_endereco")
        private byte[] comprovanteEndereco;

        @NotBlank(message = "A razão social é obrigatória.")
        private String razaoSocial;

        @NotBlank(message = "O nome do responsável é obrigatório.")
        private String responsavel;

        @NotBlank(message = "O e-mail é obrigatório.")
        @Email(message = "O e-mail deve estar no formato correto.")
        @Column(unique = true, nullable = false)
        private String email;

        @Size(max = 60)
        @NotBlank(message = "A senha é obrigatória.")
        private String senha;

        @Column(nullable = false)
        private boolean isBloqueada = false;
}
