package com.cyrillo.ativo.infra.config;

import com.cyrillo.ativo.core.dataprovider.AtivoRepositorioInterface;
import com.cyrillo.ativo.core.dataprovider.DataProviderInterface;
import com.cyrillo.ativo.core.dataprovider.LoggingInterface;
import com.cyrillo.ativo.infra.dataprovider.AtivoRepositorioImplMemoria;
import com.cyrillo.ativo.infra.dataprovider.LoggingInterfaceImplConsole;

import java.util.UUID;

public class Aplicacao implements DataProviderInterface {
    private static Aplicacao instance;
    private LoggingInterface logAplicacao;
    private AtivoRepositorioInterface ativoRepositorio;

    private Aplicacao(){
        this.logAplicacao = new LoggingInterfaceImplConsole();
        // Precisa ler os parametros de configura da aplicação e identificar se instancia Repositorio em memoria ou jdbc
        this.ativoRepositorio = new AtivoRepositorioImplMemoria();
        // this.ativoRepositorio = new AtivoRepositorioImplcomJDBC();
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
        return this.logAplicacao;
    }

    public AtivoRepositorioInterface getAtivoRepositorio() {
        return ativoRepositorio;
    }

    public void setAtivoRepositorio(AtivoRepositorioInterface ativoRepositorio) {
        this.ativoRepositorio = ativoRepositorio;
    }

    public LoggingInterface gerarNovoObjetoLog() {
        // deve ler parametros de configurar e instanciar a implementação correta de log
        // Por enquanto so tem uma implementacao
        // pode ser usado para o log de cada sessao / requisição
        return new LoggingInterfaceImplConsole();
    }

    public UUID getUniqueKey(){
        return null;
    }
}
