package com.cyrillo.ativo.infra.config;

import com.cyrillo.ativo.core.dataprovider.AtivoRepositorioInterface;
import com.cyrillo.ativo.core.dataprovider.DataProviderInterface;
import com.cyrillo.ativo.core.dataprovider.LoggingInterface;

public class Aplicacao implements DataProviderInterface {
    private static Aplicacao instance;
    private LoggingInterface loggingInterface;
    private AtivoRepositorioInterface ativoRepositorio;

    private Aplicacao(){
    }


    public static Aplicacao getInstance(){
        if(instance == null){
            synchronized (Aplicacao.class) {
                if(instance == null){
                    instance = new Aplicacao();
                }
            }
        }
        return instance;
    }

    public LoggingInterface getLoggingInterface() {
        return loggingInterface;
    }

    public void setLoggingInterface(LoggingInterface loggingInterface) {
        this.loggingInterface = loggingInterface;
    }


    public AtivoRepositorioInterface getAtivoRepositorio() {
        return ativoRepositorio;
    }

    public void setAtivoRepositorio(AtivoRepositorioInterface ativoRepositorio) {
        this.ativoRepositorio = ativoRepositorio;
    }
}
