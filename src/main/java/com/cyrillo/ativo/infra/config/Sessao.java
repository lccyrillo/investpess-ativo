package com.cyrillo.ativo.infra.config;

import com.cyrillo.ativo.core.dataprovider.AtivoRepositorioInterface;
import com.cyrillo.ativo.core.dataprovider.ConexaoInterface;
import com.cyrillo.ativo.core.dataprovider.DataProviderInterface;
import com.cyrillo.ativo.core.dataprovider.LogInterface;

import java.util.UUID;

public class Sessao implements DataProviderInterface {

    private UUID uniqueKey;
    private LogInterface log;

    public Sessao(){
        this.uniqueKey = UUID.randomUUID();
        this.log = Aplicacao.getInstance().gerarNovoObjetoLog();
    }

    public UUID getUniqueKey() {
        return uniqueKey;
    }

    @Override
    public boolean healthCheckOk(DataProviderInterface data) {
        return Aplicacao.getInstance().healthCheckOk(data);
    }

    @Override
    public int getTimeOutDefault() {
        return Aplicacao.getInstance().getTimeOutDefault();
    }

    @Override
    public DataProviderInterface geraSessao() {
        return this;
    }


    public LogInterface getLoggingInterface() {
        return this.log;
    }

    public ConexaoInterface getConexaoAplicacao() {
        return ConexaoConfig.getInstance();
    }

    public AtivoRepositorioInterface getAtivoRepositorio() {
        return Aplicacao.getInstance().getAtivoRepositorio();
    }

    public void setAtivoRepositorio(AtivoRepositorioInterface ativoRepositorio) {
        Aplicacao.getInstance().setAtivoRepositorio(ativoRepositorio);
    }
}
