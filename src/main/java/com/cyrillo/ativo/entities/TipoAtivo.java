package com.cyrillo.ativo.entities;

public class TipoAtivo {
    private int tipoAtivo;

    public TipoAtivo(int tipoAtivo) {
        if (tipoAtivo > 3 ||  tipoAtivo < 1 ){
            throw new IllegalArgumentException("Tipo ativo inválido na criação do objeto TipoAtivo");
        }
        this.tipoAtivo = tipoAtivo; //// 1 - ação , 2 - fundo imobiliario, 3 opção
    }

    public int getTipoAtivo() {
        return tipoAtivo;
    }
}