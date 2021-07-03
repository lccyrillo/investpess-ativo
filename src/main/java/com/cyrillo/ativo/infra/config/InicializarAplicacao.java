package com.cyrillo.ativo.infra.config;

import com.cyrillo.ativo.core.dataprovider.AtivoRepositorioInterface;
import com.cyrillo.ativo.core.dataprovider.LoggingInterface;
import com.cyrillo.ativo.infra.dataprovider.AtivoRepositorioImplMemoria;
import com.cyrillo.ativo.infra.dataprovider.LoggingInterfaceImplConsole;
import com.cyrillo.ativo.infra.entrypoint.servicogrpc.AtivoServerGRPC;

public class InicializarAplicacao {

    // Inicialização de configurações --> por enquanto estou pegando a implementação de log em console.
    // Refatoração Pendente 1 - Buscar configurações da classe config para saber qual interface de logging buscar

    public InicializarAplicacao() {
        // Cria objeto Aplicacao
        Aplicacao aplicacao = Aplicacao.getInstance();
        // Instancia os data providers necessarios e guarda na aplicação
        LoggingInterface log = new LoggingInterfaceImplConsole();
        aplicacao.setLoggingInterface(log);
        AtivoRepositorioInterface repo = new AtivoRepositorioImplMemoria();
        aplicacao.setAtivoRepositorio(repo);

        log.logInfo(null,"Iniciando aplicação ..." );

        // Cria o servidor GRPC
        AtivoServerGRPC var = new AtivoServerGRPC();
    }
}
