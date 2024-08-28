package br.edu.ifpb.pweb2.estagiotrack.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class CompetenciaTemplate {

    @Id
    private String id;

    private String nome;

    private String iconeUrl;
}

// SQL para gerar e popular a tabela read-only de competências a ser utilizada nos templates.

// CREATE TABLE competenciasTemplate (
//     id VARCHAR(50) PRIMARY KEY,
//     nome VARCHAR(100) NOT NULL,
//     icone_url VARCHAR(255) NOT NULL
// );

// INSERT INTO competenciasTemplate (id, nome, icone_url) VALUES
// ('C', 'C', 'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/c/c-original.svg'),
// ('C#', 'C#', 'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/csharp/csharp-original.svg'),
// ('C++', 'C++', 'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/cplusplus/cplusplus-original.svg'),
// ('CSS', 'CSS', 'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/css3/css3-original.svg'),
// ('HTML', 'HTML', 'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/html5/html5-original.svg'),
// ('Java', 'Java', 'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original.svg'),
// ('JavaScript', 'JavaScript', 'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/javascript/javascript-original.svg'),
// ('Python', 'Python', 'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/python/python-original.svg'),
// ('Ruby', 'Ruby', 'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/ruby/ruby-original.svg'),
// ('SQL', 'SQL', 'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/mysql/mysql-original.svg');

// SELECT * FROM competenciasTemplate