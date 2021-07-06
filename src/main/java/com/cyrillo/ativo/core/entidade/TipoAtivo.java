package com.cyrillo.ativo.core.entidade;

import com.cyrillo.ativo.core.entidade.excecao.ParametroTipoInvalidoException;

public class TipoAtivo {
    private int tipoAtivo;

    public TipoAtivo(int tipoAtivo) throws ParametroTipoInvalidoException {
        if (tipoAtivo > 3 ||  tipoAtivo < 1 ){
            throw new ParametroTipoInvalidoException("Tipo ativo inválido na criação do objeto TipoAtivo");
        }
        this.tipoAtivo = tipoAtivo; //// 1 - ação , 2 - fundo imobiliario, 3 opção
    }

    public int getTipoAtivo() {
        return tipoAtivo;
    }
}