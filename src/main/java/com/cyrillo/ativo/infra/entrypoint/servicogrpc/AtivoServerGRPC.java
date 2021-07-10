package com.cyrillo.ativo.infra.entrypoint.servicogrpc;

import com.cyrillo.ativo.core.dataprovider.DataProviderInterface;
import com.cyrillo.ativo.core.dataprovider.LogInterface;
import com.cyrillo.ativo.infra.config.Aplicacao;
import io.grpc.*;

import java.util.ArrayList;
import java.util.List;


public class AtivoServerGRPC {
    private DataProviderInterface data;

    public AtivoServerGRPC(DataProviderInterface dataProviderInterface) {
        this.data = dataProviderInterface;
        this.inicializaAtivoServer();
    }

    private void inicializaAtivoServer() {
        LogInterface log = Aplicacao.getInstance().getLoggingInterface();
        log.logInfo(null,"Inicializando Servidor GRPC.");


        try {
            List<ServerServiceDefinition> lista = new ArrayList<>();
            lista.add(new AtivoServerService(this.data).bindService());
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
