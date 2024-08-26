package br.edu.ifpb.pweb2.estagiotrack.model;

import br.edu.ifpb.pweb2.estagiotrack.validation.Unique;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Aluno {

    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Email
    @NotBlank(message= "Campo obrigatório!")
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank(message= "Campo obrigatório!")
    @Column(unique = true, nullable = false)
    private String nomeUsuario;

    @NotBlank(message= "A senha não pode ser vazia!")
    private String senha;

    @NotBlank(message= "Campo obrigatório!")
    private String nome;

    @NotBlank(message= "Campo obrigatório!")
    private String genero;

    @ElementCollection
    @NotEmpty(message="Selecione pelo menos um competência")
    private List<String> competencias;
}
