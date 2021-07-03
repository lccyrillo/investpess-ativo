package com.cyrillo.ativo.infra.config;

import com.cyrillo.ativo.core.dataprovider.AtivoRepositorioInterface;
import com.cyrillo.ativo.core.dataprovider.LoggingInterface;
import com.cyrillo.ativo.core.entidade.Aplicacao;
import com.cyrillo.ativo.infra.dataprovider.AtivoRepositorioImplMemoria;
import com.cyrillo.ativo.infra.dataprovider.LoggingInterfaceImplConsole;
import com.cyrillo.ativo.infra.entrypoint.servicogrpc.AtivoServerGRPC;

public class InicializarAplicacao {

    // Inicialização de configurações --> por enquanto estou pegando a implementação de log em console.
    // Refatoração Pendente 1 - Buscar configurações da classe config para saber qual interface de logging buscar

    public InicializarAplicacao() {
        LoggingInterface loggingInterface = new LoggingInterfaceImplConsole();
        loggingInterface.logInfo(null,"Iniciando aplicação ..." );
        Aplicacao aplicacao = Aplicacao.getInstance();
        aplicacao.setLoggingInterface(loggingInterface);
        // instancia o repositorio correto e passa para aplicação
        //AtivoRepositorioInterface ativoRepositorio = new AtivoRepositorioImplcomJDBC();
        AtivoRepositorioInterface ativoRepositorio = new AtivoRepositorioImplMemoria();
        aplicacao.setAtivoRepositorio(ativoRepositorio);
        loggingInterface.logInfo(null,"Repositório inicializado" );
        try {
            AtivoServerGRPC var = new AtivoServerGRPC();
            loggingInterface.logInfo(null, "Servidor GRPC inicializado");
            loggingInterface.logInfo(null, "Inicialização da aplicação concluída.");
        }
        catch (Exception e){
            loggingInterface.logError(null, "Falha na inicialização do servidor GRPC.");
            loggingInterface.logError(null, e.getMessage());
        }
    }
}
