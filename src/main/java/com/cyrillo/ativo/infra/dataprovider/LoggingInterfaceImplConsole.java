package com.cyrillo.ativo.infra.dataprovider;

import com.cyrillo.ativo.core.dataprovider.tipos.LoggingInterface;


public class LoggingInterfaceImplConsole implements LoggingInterface {
    private static LoggingInterfaceImplConsole instance;

    public LoggingInterfaceImplConsole() {
    }

    public static LoggingInterfaceImplConsole getInstance(){
        if(instance == null){
            synchronized (LoggingInterfaceImplConsole.class) {
                if(instance == null){
                    instance = new LoggingInterfaceImplConsole();
                }
            }

        }
        return instance;
    }

    @Override
    public void logError(String flowid, String mensagem) {
        System.out.println("Level: Error | Flowid: " + flowid + " Mensagem: " +mensagem);
    }

    @Override
    public void logInfo(String flowid, String mensagem) {
        System.out.println("Level: Info | Flowid: " + flowid + " Mensagem: " +mensagem);
    }
}
