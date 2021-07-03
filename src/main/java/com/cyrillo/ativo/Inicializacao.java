package com.cyrillo.ativo;

import com.cyrillo.ativo.infra.config.Aplicacao;

public class Inicializacao {

    public static void main(String[] args)  {
        Aplicacao.getInstance().inicializaAplicacao();
    }
}
