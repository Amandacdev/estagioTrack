package br.edu.ifpb.pweb2.estagiotrack.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Empresa implements Serializable {

   @Id
   @GeneratedValue
   private Integer id;

   /*@Pattern(
           regexp = "\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}|\\d{14}",
           message = "O CNPJ deve estar no formato 99.999.999/9999-99 ou 99999999999999."
   )*/
   @NotBlank(message = "O CNPJ é obrigatório.")
   private String cnpj;

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
   /*@Pattern(
           regexp = "\\(\\d{2}\\) \\d{5}-\\d{4}|\\d{5}-\\d{4}|\\d{10}",
           message = "O número de celular deve estar no formato (99) 99999-9999, 99999-9999 ou 9999999999."
   )*/
   private String telefone;

   @NotBlank(message = "A atividade principal é obrigatória.")
   private String atividadePrincipal;

   private String site;

   private byte[] comprovanteEndereco;

   @NotBlank(message = "A razão social é obrigatória.")
   @Column(unique = true, nullable = false)
   private String razaoSocial;

   @NotBlank(message = "O nome do responsável é obrigatório.")
   private String responsavel;

   @NotBlank(message = "O e-mail é obrigatório.")
   @Email(message = "O e-mail deve estar no formato correto.")
   @Column(unique = true, nullable = false)
   private String email;

   @NotBlank(message = "A senha é obrigatória.")
   private String senha;

}
