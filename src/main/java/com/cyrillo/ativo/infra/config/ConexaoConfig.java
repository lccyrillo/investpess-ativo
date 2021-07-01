package com.cyrillo.ativo.infra.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoConfig {
    private static ConexaoConfig instance;
    private Connection conexaoBD;
    private ConexaoConfig() {
        System.out.println("Entrou no Conexao config");
        // variaveis de anbiente recebidas pelo Docker
        String db_host = System.getenv("DB_HOST");
        String db_port = System.getenv("DB_PORT");
        if (db_host == null) {
            db_host = "localhost"; //ambiente local
        }
        if (db_port == null) {
            db_port = "5433"; // ambiente local
        }
        String url = "jdbc:postgresql://" + db_host + ":" + db_port + "/investpess_ativo";
        System.out.println("URL" + url);

        // produção - String url = "jdbc:postgresql://db:5432/investpess_ativo";
        // dev - String url = "jdbc:postgresql://localhost:5433/investpess_ativo";

        try {
            Class.forName("org.postgresql.Driver");
            this.conexaoBD = DriverManager.getConnection(url, "postgres",null);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            this.conexaoBD = null;
        }
        catch (ClassNotFoundException e) {
            this.conexaoBD = null;
            e.printStackTrace();
        }
    }

    public static ConexaoConfig getInstance(){
        if(instance == null){
            synchronized (ConexaoConfig.class) {
                if(instance == null){
                    instance = new ConexaoConfig();
                }
            }

        }
        return instance;
    }

    public Connection getConnection() {
        return this.conexaoBD;
    }
}