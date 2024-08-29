package br.edu.ifpb.pweb2.estagiotrack.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Email(message = "Por favor, insira um email válido.")
    @NotBlank(message = "O email é obrigatório.")
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank(message = "O nome de usuário é obrigatório.")
    @Column(unique = true, nullable = false)
    private String nomeUsuario;

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
