package com.cyrillo.ativo.core.usecase;

import com.cyrillo.ativo.core.dataprovider.LoggingInterface;
import com.cyrillo.ativo.core.entidade.Aplicacao;

public class IdentificarLogginInterface {
    public IdentificarLogginInterface(){
    }
    public LoggingInterface getLoggingInterface(){
        return Aplicacao.getInstance().getLoggingInterface();
    }
}
