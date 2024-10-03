package br.edu.ifpb.pweb2.estagiotrack.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultadoEstagio {

    private String alunoNome;
    private String ofertanteRazaoSocial;
    private String ofertanteCnpj;

}
