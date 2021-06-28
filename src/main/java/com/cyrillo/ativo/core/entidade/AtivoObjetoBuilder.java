package com.cyrillo.ativo.core.entidade;

public class AtivoObjetoBuilder {
    private AtivoObjeto ativoObjeto;
    public AtivoObjetoBuilder() {}
    public AtivoObjetoBuilder infoCompleta(String sigla, String nomeAtivo, String descricaoCNPJAtivo, int tipoAtivo){
        this.ativoObjeto = new AtivoObjeto(sigla, nomeAtivo,descricaoCNPJAtivo, new TipoAtivo(tipoAtivo));
        return this;
    }
    public AtivoObjeto build(){
        return this.ativoObjeto;
    }
}

