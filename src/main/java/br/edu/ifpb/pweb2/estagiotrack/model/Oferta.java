package br.edu.ifpb.pweb2.estagiotrack.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Data
public class Oferta implements Serializable {

    @Setter
    @Getter
    public Integer id;
    public Empresa ofertante;
    public String emailOfertante; //Parte do workaround para vincular oferta a empresa dinamicamente.
    public String tituloCargo;
    public String valorBolsa;
    public String turno;

    public Oferta(Integer id, Empresa ofertante, String emailOfertante, String tituloCargo, String valorBolsa, String turno) {
        this.id = id;
        this.ofertante = ofertante;
        this.emailOfertante = emailOfertante;
        this.tituloCargo = tituloCargo;
        this.valorBolsa = valorBolsa;
        this.turno = turno;
    }
}

//OBS: Mockando uma empresa teste pra assinar as ofertas enquanto não tem login
