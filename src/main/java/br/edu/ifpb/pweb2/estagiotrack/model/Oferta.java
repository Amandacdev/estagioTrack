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
    public String tituloCargo;
    public String valorBolsa;
    public String turno;

    public Oferta(Integer id, Empresa ofertante, String tituloCargo, String valorBolsa, String turno) {
        this.id = id;
        this.ofertante = new Empresa(1, "Empresa Teste", "Responsável Teste", "email@teste.com", "123");
        this.tituloCargo = tituloCargo;
        this.valorBolsa = valorBolsa;
        this.turno = turno;
    }
}

//OBS: Mockando uma empresa teste pra assinar as ofertas enquanto não tem login
