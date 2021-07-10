package com.cyrillo.ativo.infra.config;

import com.cyrillo.ativo.core.dataprovider.ConexaoInterface;
import com.cyrillo.ativo.core.dataprovider.excecao.FalhaObterConexaoRepoDataProvExcecao;

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

    public Connection getConnection() throws FalhaObterConexaoRepoDataProvExcecao {
        try {
            if (! conexaoAtiva) {
                geraConexao();
            }
        }
        catch (Exception e) {
            this.conexaoAtiva = false;
            FalhaObterConexaoRepoDataProvExcecao falha = new FalhaObterConexaoRepoDataProvExcecao("Falha para obter conexão com repositório.");
            falha.addSuppressed(e);
            throw falha;
        }
        return this.conexaoBD;
    }

    public void setConnectionAtiva(boolean conexaoAtiva)  {
        this.conexaoAtiva = conexaoAtiva;
    }

    private void geraConexao() throws SQLException, ClassNotFoundException  {
        if (! conexaoAtiva) {

            String url = Aplicacao.getInstance().getStringConexaoBD();
            System.out.println("URL" + url);
            Class.forName("org.postgresql.Driver");
            this.conexaoBD = DriverManager.getConnection(url,Aplicacao.getInstance().getUserDB(), Aplicacao.getInstance().getPasswordDB());
            this.conexaoAtiva = true;
        }
    }


}