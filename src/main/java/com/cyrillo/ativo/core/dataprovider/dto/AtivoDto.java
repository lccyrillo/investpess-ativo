package com.cyrillo.ativo.core.dataprovider.dto;

import com.cyrillo.ativo.core.dataprovider.tipos.AtivoDtoInterface;
import com.cyrillo.ativo.core.entidade.AtivoObjeto;
import com.cyrillo.ativo.core.entidade.TipoAtivo;
import com.cyrillo.ativo.core.entidade.excecao.AtivoParametrosInvalidosException;

public class AtivoDto implements AtivoDtoInterface {
    private String sigla;
    private String nomeAtivo;
    private String descricaoCNPJAtivo;
    private int tipoAtivo;

    public AtivoDto(String sigla, String nomeAtivo, String descricaoCNPJAtivo, int tipoAtivo) {
        this.sigla = sigla;
        this.nomeAtivo = nomeAtivo;
        this.descricaoCNPJAtivo = descricaoCNPJAtivo;
        this.tipoAtivo = tipoAtivo;
    }

    public AtivoObjeto builder() throws AtivoParametrosInvalidosException  {
        return new AtivoObjeto(sigla,nomeAtivo,descricaoCNPJAtivo,new TipoAtivo(tipoAtivo));
    }
    public String getSigla() {
        return sigla;
    }

    public String getNomeAtivo() {
        return nomeAtivo;
    }

    public String getDescricaoCNPJAtivo() {
        return descricaoCNPJAtivo;
    }

    public int getTipoAtivoInt() {
        return tipoAtivo;
    }

}
