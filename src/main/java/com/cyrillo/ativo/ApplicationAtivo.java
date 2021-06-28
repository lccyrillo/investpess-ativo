package com.cyrillo.ativo;

import com.cyrillo.ativo.infra.entrypoint.servicogrpc.AtivoServerGRPC;

import java.io.IOException;

public class ApplicationAtivo {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Inicialização da aplicação Ativo");
        AtivoServerGRPC var = new AtivoServerGRPC();
    }
}
