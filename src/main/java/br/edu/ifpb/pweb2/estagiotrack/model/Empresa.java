package br.edu.ifpb.pweb2.estagiotrack.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Data
public class Empresa implements Serializable {

    @Setter
    @Getter
    public Integer id;
    public String cnpj;
    public String cep;
    public String logradouro;
    public String numeroImovel;
    public String bairro;
    public String cidade;
    public String estado;
    public String telefone;
    public String atividadePrincipal;
    public String site;
    public byte[] comprovanteEndereco;
    public String razaoSocial;
    public String responsavel;
    public String email;
    public String senha;

    public Empresa(Integer id, String cnpj, String cep, String logradouro, String numeroImovel,
                   String bairro, String cidade, String estado, String telefone, 
                   String atividadePrincipal, String site, byte[] comprovanteEndereco, 
                   String razaoSocial, String responsavel, String email, String senha) {
        this.id = id;
        this.cnpj = cnpj;
        this.cep = cep;
        this.logradouro = logradouro;
        this.numeroImovel = numeroImovel;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.telefone = telefone;
        this.atividadePrincipal = atividadePrincipal;
        this.site = site;
        this.comprovanteEndereco = comprovanteEndereco;
        this.razaoSocial = razaoSocial;
        this.responsavel = responsavel;
        this.email = email;
        this.senha = senha;
    }
}
