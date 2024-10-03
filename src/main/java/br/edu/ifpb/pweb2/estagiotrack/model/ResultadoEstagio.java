package br.edu.ifpb.pweb2.estagiotrack.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ResultadoEstagio {

    private String alunoNome;
    private String ofertanteRazaoSocial;
    private String ofertanteCnpj;

}
