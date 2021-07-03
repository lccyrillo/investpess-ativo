package com.cyrillo.ativo.infra.entrypoint.servicogrpc;

import com.cyrillo.ativo.core.dataprovider.LoggingInterface;
import com.cyrillo.ativo.infra.config.Aplicacao;
import io.grpc.Server;
import io.grpc.ServerBuilder;



public class AtivoServerGRPC {
    public AtivoServerGRPC() {
        this.inicializaAtivoServer();
    }

    private void inicializaAtivoServer() {
        LoggingInterface log = Aplicacao.getInstance().getLoggingInterface();
        log.logInfo(null,"Inicializando Servidor GRPC.");

        try {
            Server server = ServerBuilder.forPort(50051)
                    .addService(new CadastraAtivoObjetoService())
                    .addService(new ConsultaAtivoObjetoService())
                    .build();
            server.start();
            Runtime.getRuntime().addShutdownHook(new Thread( () -> {
                log.logInfo(null,"Recebida solicitação para encerramento do servidor.");
                server.shutdown();
                log.logInfo(null,"Servidor GRPC encerrado com sucesso!");

            }  ));
            log.logInfo(null,"Servidor GRPC inicializado com sucesso!");
            server.awaitTermination();
        }
        catch (Exception e){
            log.logError(null, "Falha na inicialização do servidor GRPC.");
            log.logError(null, e.getMessage());
        }

    }
}
