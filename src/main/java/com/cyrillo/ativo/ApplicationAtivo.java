package com.cyrillo.ativo;

import com.cyrillo.ativo.infra.entrypoints.AtivoServer;

import java.io.IOException;

public class ApplicationAtivo {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Inicialização da aplicação Ativo");
        AtivoServer var = new AtivoServer();
    }
}
