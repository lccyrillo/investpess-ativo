package com.cyrillo.ativo.core.dataprovider;


import com.cyrillo.ativo.core.usecase.excecao.ComunicacaoLogException;

public interface LogInterface {
    public void logError(String flowid, String mensagem);
    public void logInfo(String flowid, String mensagem);
    void healthCheck(DataProviderInterface data) throws ComunicacaoLogException;

}
