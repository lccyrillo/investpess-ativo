package com.cyrillo.ativo.core.dataprovider.tipos;


public interface LoggingInterface {
    public void logError(String flowid, String mensagem);
    public void logInfo(String flowid, String mensagem);

}
