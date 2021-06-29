package com.cyrillo.ativo.core.entidade;

import com.cyrillo.ativo.core.dataprovider.LoggingInterface;

public class Aplicacao {
    private static Aplicacao instance;
    private LoggingInterface loggingInterface;

    public Aplicacao(){
    }

    public LoggingInterface getLoggingInterface() {
        return loggingInterface;
    }

    public void setLoggingInterface(LoggingInterface loggingInterface) {
        this.loggingInterface = loggingInterface;
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


}
