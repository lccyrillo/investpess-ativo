package com.cyrillo.ativo.infra.entrypoint.servicogrpc;

import com.cyrillo.ativo.core.dataprovider.DataProviderInterface;
import com.cyrillo.ativo.core.dataprovider.LogInterface;

// Avaliar melhor as importacoes. O Entry poiny não deveria referenciar frameworks externos.
import com.cyrillo.ativo.infra.config.Aplicacao;
import io.grpc.*;
import io.opentracing.Tracer;
import io.opentracing.contrib.grpc.TracingServerInterceptor;
import io.opentracing.util.GlobalTracer;


import java.util.ArrayList;
import java.util.List;


public class AtivoServerGRPC {
    private DataProviderInterface data;
    private final Tracer tracer;

    public AtivoServerGRPC(DataProviderInterface dataProviderInterface) {
        this.data = dataProviderInterface;
        tracer = GlobalTracer.get();
        this.inicializaAtivoServer();

    }

    private void inicializaAtivoServer() {
        
        LogInterface log = Aplicacao.getInstance().getLoggingInterface();
        log.logInfo(null,"Inicializando Servidor GRPC.");


        try {
            // duas responsabilidades nesse metodo de inicialização.
            // o servidor GRPC deveria instanciar apenas ele proprio
            // se a configuracao de jaeger estiver ligada, o servidor grpc deveria adicionar servicos incluindo o interceptador do jaeger, caso contráriop nao.
            // Preciso criar um servidor de tracing
            TracingServerInterceptor tracingInterceptor = Aplicacao.getInstance().geTtracingServerInterceptor();


            List<ServerServiceDefinition> lista = new ArrayList<>();
            AtivoServerService ativoServerService = new AtivoServerService(this.data);
            HealthCheckService healthCheckService = new HealthCheckService();


            lista.add(ativoServerService.bindService());
            lista.add(healthCheckService.bindService());
            Server server = ServerBuilder.forPort(50051)
//                    .addServices(lista)
                    .addService(tracingInterceptor.intercept(ativoServerService))
                    .addService(tracingInterceptor.intercept(healthCheckService))
                    .build()
                    .start();

            Runtime.getRuntime().addShutdownHook(new Thread( () -> {
                log.logInfo(null,"Recebida solicitação para encerramento do servidor.");
                server.shutdown();
                log.logInfo(null,"Servidor GRPC encerrado com sucesso!");

            }  ));
            log.logInfo(null,"Servidor GRPC inicializado com sucesso!");
            log.logInfo(null,listaMetodosServico(lista));
            server.awaitTermination();
        }
        catch (Exception e){
            log.logError(null, "Falha na inicialização do servidor GRPC.");
            log.logError(null, e.getMessage());
        }

    }

    private String listaMetodosServico(List<ServerServiceDefinition> lista){
        String mensagem = "";
        for (int i =0; i <lista.size(); i++) {
            mensagem = mensagem + "Serviço: ";
                    mensagem = mensagem + lista.get(i).getServiceDescriptor().getName() + " Métodos: ";
            for (ServerMethodDefinition<?, ?> methodDesc : lista.get(i).getMethods()) {
                mensagem = mensagem + methodDesc.getMethodDescriptor().getBareMethodName() + " ";
            }
        }
        return mensagem;
    }
}
