package br.edu.ifpb.pweb2.estagiotrack.model;

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
    public String email;

    @NotBlank(message= "Campo obrigatório!")
    public String nomeUsuario;

    @NotBlank(message= "A senha não pode ser vazia!")
    private String senha;

    @NotBlank(message= "Campo obrigatório!")
    public String nome;

    @NotBlank(message= "Campo obrigatório!")
    public String genero;

    @ElementCollection
    public List<String> competencias;
}
