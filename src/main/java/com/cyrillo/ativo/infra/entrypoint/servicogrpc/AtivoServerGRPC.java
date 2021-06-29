package com.cyrillo.ativo.infra.entrypoint.servicogrpc;

import com.cyrillo.ativo.core.dataprovider.LoggingInterface;
import com.cyrillo.ativo.core.usecase.IdentificarLogginInterface;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;



public class AtivoServerGRPC {
    public AtivoServerGRPC() throws IOException, InterruptedException {
        this.inicializaAtivoServer();
    }

    private void inicializaAtivoServer() throws IOException, InterruptedException{
        IdentificarLogginInterface identificarLogginInterface = new IdentificarLogginInterface();
        LoggingInterface loggingInterface = identificarLogginInterface.getLoggingInterface();
        loggingInterface.logInfo(null,"Inicializando Servidor GRPC.");


        Server server = ServerBuilder.forPort(50051)
                .addService(new CadastraAtivoObjetoService())
                .addService(new ConsultaAtivoObjetoService())
                .build();
        server.start();

        loggingInterface.logInfo(null,"Serviço GRPC inicializado");
        Runtime.getRuntime().addShutdownHook(new Thread( () -> {
            loggingInterface.logInfo(null,"Received shutdown request");
            server.shutdown();
            loggingInterface.logInfo(null,"Sucessfuly shutdown the server!");

        }  ));

        server.awaitTermination();
    }
}
