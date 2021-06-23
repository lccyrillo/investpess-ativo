package com.cyrillo.ativo;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;


public class AtivoServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Hello from Ativo Server com serviço1!");
        System.out.println("Hello from Ativo Server com serviço2!");
        Server server = ServerBuilder.forPort(50051)
                .addService(new CadastraAtivoObjetoServiceImpl())
                .addService(new ConsultaAtivoObjetoServiceImpl())
                .build();
        System.out.println("Hello from Ativo Server com serviço3!");
        server.start();
        Runtime.getRuntime().addShutdownHook(new Thread( () -> {
            System.out.println("Received shutdown request");
            server.shutdown();
            System.out.println("Sucessfuly shutdown the server!");
        }  ));

        server.awaitTermination();

    }

}
