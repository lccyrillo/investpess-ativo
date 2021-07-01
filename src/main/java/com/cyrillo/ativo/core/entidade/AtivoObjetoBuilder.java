package com.cyrillo.ativo.core.entidade;

import com.cyrillo.ativo.core.excecao.AtivoParametrosInvalidosException;

public class AtivoObjetoBuilder {
    private AtivoObjeto ativoObjeto;
    public AtivoObjetoBuilder() {}
    public AtivoObjetoBuilder infoCompleta(String sigla, String nomeAtivo, String descricaoCNPJAtivo, int tipoAtivo) throws AtivoParametrosInvalidosException {
        this.ativoObjeto = new AtivoObjeto(sigla, nomeAtivo,descricaoCNPJAtivo, new TipoAtivo(tipoAtivo));
        return this;
    }
    public AtivoObjeto build(){
        return this.ativoObjeto;
    }
}

