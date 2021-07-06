package com.cyrillo.ativo.core.dataprovider;


public interface LogInterface {
    public void logError(String flowid, String mensagem);
    public void logInfo(String flowid, String mensagem);

}
