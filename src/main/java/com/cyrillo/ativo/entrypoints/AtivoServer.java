package com.cyrillo.ativo.entrypoints;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;


public class AtivoServer {
    public AtivoServer() throws IOException, InterruptedException {
        this.inicializaAtivoServer();
    }

    private void inicializaAtivoServer() throws IOException, InterruptedException{
        System.out.println("Inicializando Ativo Server.");
        Server server = ServerBuilder.forPort(50051)
                .addService(new CadastraAtivoObjetoService())
                .addService(new ConsultaAtivoObjetoService())
                .build();
        server.start();
        System.out.println("Serviço inicializado");
        Runtime.getRuntime().addShutdownHook(new Thread( () -> {
            System.out.println("Received shutdown request");
            server.shutdown();
            System.out.println("Sucessfuly shutdown the server!");
        }  ));

        server.awaitTermination();
    }
}
