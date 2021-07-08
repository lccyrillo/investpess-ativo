package com.cyrillo.ativo.infra.config;

import com.cyrillo.ativo.core.dataprovider.ConexaoInterface;
import com.cyrillo.ativo.core.dataprovider.excecao.FalhaObterConexaoRepositorioExcecao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoConfig implements ConexaoInterface {
    private static ConexaoConfig instance;
    private Connection conexaoBD;
    private boolean conexaoAtiva;
    private ConexaoConfig() {
        this.conexaoAtiva = false;
        System.out.println("Entrou no Conexao config");
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

    public Connection getConnection() throws FalhaObterConexaoRepositorioExcecao {
        try {
            if (! conexaoAtiva) {
                geraConexao();
            }
        }
        catch (Exception e) {
            this.conexaoAtiva = false;
            FalhaObterConexaoRepositorioExcecao falha = new FalhaObterConexaoRepositorioExcecao("Falha para obter conexão com repositório.");
            falha.addSuppressed(e);
            throw falha;
        }
        return this.conexaoBD;
    }

    public void setConnectionAtiva(boolean conexaoAtiva)  {
        this.conexaoAtiva = conexaoAtiva;
    }

    // Ainda precisa ser refatorada para pegar configuraçoes da aplicacao
    private void geraConexao() throws SQLException, ClassNotFoundException  {
        if (! conexaoAtiva) {
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

            Class.forName("org.postgresql.Driver");
            this.conexaoBD = DriverManager.getConnection(url, "postgres",null);
            this.conexaoAtiva = true;
        }
    }

}