package com.cyrillo.ativo;

import com.cyrillo.ativo.core.dataprovider.LoggingInterface;
import com.cyrillo.ativo.core.usecase.InicializarAplicacao;
import com.cyrillo.ativo.infra.dataprovider.LoggingInterfaceImplConsole;
import com.cyrillo.ativo.infra.entrypoint.servicogrpc.AtivoServerGRPC;

import java.io.IOException;

public class Inicializacao {

    public static void main(String[] args) throws IOException, InterruptedException {
        // Inicialização de configurações --> por enquanto estou pegando a implementação de log em console.
        // Refatoração Pendente 1 - Buscar configurações da classe config para saber qual interface de logging buscar
        LoggingInterface loggingInterface = new LoggingInterfaceImplConsole();
        InicializarAplicacao inicializarAplicacao = new InicializarAplicacao(loggingInterface);
        loggingInterface.logInfo(null,"Inicialização da aplicação Ativo" );
        AtivoServerGRPC var = new AtivoServerGRPC();
    }
}
