package br.edu.ifpb.pweb2.estagiotrack.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Aluno {

    @Id
    private Integer id;

    @Email(message = "Por favor, insira um email válido.")
    @NotBlank(message = "O email é obrigatório.")
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank(message = "O nome de usuário é obrigatório.")
    @Column(unique = true, nullable = false)
    private String nomeUsuario;

    @Size(max = 60)
    @NotBlank(message = "A senha é obrigatória e não pode estar vazia.")
    private String senha;

    @NotBlank(message = "O nome completo é obrigatório.")
    private String nome;

    @NotBlank(message = "O gênero é obrigatório.")
    private String genero;

    @ElementCollection
    @NotEmpty(message = "Pelo menos uma competência deve ser selecionada.")
    private List<String> competencias;

}
