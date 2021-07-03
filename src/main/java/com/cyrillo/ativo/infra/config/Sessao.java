package com.cyrillo.ativo.infra.config;

import com.cyrillo.ativo.core.dataprovider.AtivoRepositorioInterface;
import com.cyrillo.ativo.core.dataprovider.DataProviderInterface;
import com.cyrillo.ativo.core.dataprovider.LoggingInterface;

import java.util.UUID;

public class Sessao implements DataProviderInterface {

    private UUID uniqueKey;
    private LoggingInterface log;

    public Sessao(){
        this.uniqueKey = UUID.randomUUID();
        this.log = Aplicacao.getInstance().gerarNovoObjetoLog();
    }

    public UUID getUniqueKey() {
        return uniqueKey;
    }

    public LoggingInterface getLoggingInterface() {
        return this.log;
    }

    public AtivoRepositorioInterface getAtivoRepositorio() {
        return Aplicacao.getInstance().getAtivoRepositorio();
    }
}
