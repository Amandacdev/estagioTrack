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
    // public double valorBolsa;
    public String horarios;

    public Oferta(Integer id, Empresa ofertante, String tituloCargo, String horarios) {
        this.id = id;
        this.ofertante = new Empresa(1, "Empresa Teste", "Responsável Teste", "email@teste.com", "123");
        this.tituloCargo = tituloCargo;
        // this.valorBolsa = valorBolsa;
        this.horarios = horarios;
    }
}

//OBS: Mockando uma empresa teste pra assinar as ofertas enquanto não tem login
