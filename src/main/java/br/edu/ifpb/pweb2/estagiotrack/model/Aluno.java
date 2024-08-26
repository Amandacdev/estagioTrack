package br.edu.ifpb.pweb2.estagiotrack.model;

import br.edu.ifpb.pweb2.estagiotrack.validation.Unique;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Aluno implements Serializable {

    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Email
    @NotBlank(message= "Campo obrigatório!")
    @Unique(message = "Já existe alguém com este email")
    private String email;

    @NotBlank(message= "Campo obrigatório!")
    @Unique(message = "Já existe alguém com esse nome de usuário")
    private String nomeUsuario;

    @NotBlank(message= "A senha não pode ser vazia!")
    private String senha;

    @NotBlank(message= "Campo obrigatório!")
    private String nome;

    @NotBlank(message= "Campo obrigatório!")
    private String genero;

    @ElementCollection
    @NotBlank(message="Selecione pelo menos um competência")
    private List<String> competencias;
}
