package com.cyrillo.ativo.entities;

public class TipoAtivo {
    private int tipoAtivo;

    public TipoAtivo(int tipoAtivo) {
        this.tipoAtivo = tipoAtivo; //// 1 - ação , 2 - fundo imobiliario, 3 opção
    }

    public int getTipoAtivo() {
        return tipoAtivo;
    }

    public void setTipoAtivo(int tipoAtivo) {
        this.tipoAtivo = tipoAtivo;
    }
}