package com.cyrillo.ativo.core.dataprovider;

import java.util.UUID;

// Acerto para apontar para conexao interface
public interface DataProviderInterface {
    public LogInterface getLoggingInterface();
    public ConexaoInterface getConexaoAplicacao();
    public AtivoRepositorioInterface getAtivoRepositorio();
    public UUID getUniqueKey();
    public boolean healthCheckOk(DataProviderInterface data);
    public int getTimeOutDefault();
}
