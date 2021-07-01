package com.cyrillo.ativo.core.usecase;

import com.cyrillo.ativo.core.dataprovider.LoggingInterface;
import com.cyrillo.ativo.core.entidade.Aplicacao;

public class InicializarAplicacao {
    public InicializarAplicacao(LoggingInterface loggingInterface){
        Aplicacao aplicacao = Aplicacao.getInstance();
        aplicacao.setLoggingInterface(loggingInterface);
    }
}
