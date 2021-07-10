package com.cyrillo.ativo.infra.entrypoint.servicogrpc;

import com.cyrillo.ativo.core.dataprovider.LogInterface;
import com.cyrillo.ativo.infra.config.Aplicacao;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.ServerServiceDefinition;

import java.util.ArrayList;
import java.util.List;


public class AtivoServerGRPC {
    public AtivoServerGRPC() {
        this.inicializaAtivoServer();
    }

    private void inicializaAtivoServer() {
        LogInterface log = Aplicacao.getInstance().getLoggingInterface();
        log.logInfo(null,"Inicializando Servidor GRPC.");


        try {
            List<ServerServiceDefinition> lista = new ArrayList<>();
            lista.add(new AtivoServerService().bindService());
            lista.add(new HealthCheckService().bindService());
            Server server = ServerBuilder.forPort(50051)
                    .addServices(lista)
                    .build()
                    .start();
            Runtime.getRuntime().addShutdownHook(new Thread( () -> {
                log.logInfo(null,"Recebida solicitação para encerramento do servidor.");
                server.shutdown();
                log.logInfo(null,"Servidor GRPC encerrado com sucesso!");

            }  ));
            log.logInfo(null,"Servidor GRPC inicializado com sucesso!");
            List<ServerServiceDefinition> lista2 = server.getServices();
            String mensagem = "Serviços disponíveis: ";
            for (int i =0; i <lista.size(); i++) {
                mensagem = mensagem + lista.get(i).getServiceDescriptor().getName() + " ";
                // code block to be executed
            }

            log.logInfo(null,mensagem);

            server.awaitTermination();
        }
        catch (Exception e){
            log.logError(null, "Falha na inicialização do servidor GRPC.");
            log.logError(null, e.getMessage());
        }

    }
}
