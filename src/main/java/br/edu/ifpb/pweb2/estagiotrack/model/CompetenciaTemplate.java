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
    private Integer id;

    private String nome;

    private String iconeUrl;
}

// SQL para popular a tabela read-only de competências a ser utilizada nos templates. Deve ser executado após iniciar a aplicação.

// INSERT INTO competencia_template (id, nome, icone_url) VALUES
// (1, 'C', 'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/c/c-original.svg'),
// (2, 'C#', 'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/csharp/csharp-original.svg'),
// (3, 'C++', 'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/cplusplus/cplusplus-original.svg'),
// (4, 'CSS', 'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/css3/css3-original.svg'),
// (5, 'HTML', 'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/html5/html5-original.svg'),
// (6, 'Java', 'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original.svg'),
// (7, 'JavaScript', 'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/javascript/javascript-original.svg'),
// (8, 'Python', 'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/python/python-original.svg'),
// (9, 'Ruby', 'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/ruby/ruby-original.svg'),
// (10, 'SQL', 'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/mysql/mysql-original.svg');

// SELECT * FROM competencia_template