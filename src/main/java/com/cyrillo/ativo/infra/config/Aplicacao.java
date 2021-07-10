package com.cyrillo.ativo.infra.config;

import com.cyrillo.ativo.core.dataprovider.AtivoRepositorioInterface;
import com.cyrillo.ativo.core.dataprovider.ConexaoInterface;
import com.cyrillo.ativo.core.dataprovider.DataProviderInterface;
import com.cyrillo.ativo.core.dataprovider.LogInterface;
import com.cyrillo.ativo.infra.config.excecao.PropriedadeInvalidaConfigExcecao;
import com.cyrillo.ativo.infra.dataprovider.AtivoRepositorioImplMemoria;
import com.cyrillo.ativo.infra.dataprovider.AtivoRepositorioImplcomJDBC;
import com.cyrillo.ativo.infra.dataprovider.LogInterfaceImplConsole;
import com.cyrillo.ativo.infra.entrypoint.servicogrpc.AtivoServerGRPC;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

public class Aplicacao implements DataProviderInterface {
    private static Aplicacao instance;
    private LogInterface logAplicacao;
    private AtivoRepositorioInterface ativoRepositorio;
    private String repoImplementacao;
    private String logImplementacao;
    private List<String> propriedadeLog; // lista de todos os domínios possíveis para a propriedade de log.
    private List<String> propriedadeRepo;// lista de todos os domínios possíveis para a propriedade de log.
    // Parametros de conexao com banco de dados
    private String db_host;
    private String db_port;
    private String stringConexaoBD;
    private String userDB;
    private String passwordDB;

    private Aplicacao(){
    }

    public void inicializaAplicacao(){
        try {
            // Carrega configuraçoes da aplicação
            carregarConfiguracoes();
            // Configura Data Providers (Aplicação e Repositório)
            configurarLogAplicacao();
            this.logAplicacao.logInfo(null,"Inicializando aplicação...");
            configurarRepositorioAplicacao();
            this.logAplicacao.logInfo(null,"Propriedades de configuração da aplicação carregadas!");
            this.logAplicacao.logInfo(null,getConfiguracoesAplicacao());
            // Levanta o servidor GRPC
            AtivoServerGRPC var = new AtivoServerGRPC();
        }
        catch (Exception e){
            System.out.println("Não foi possível inicializar a aplicação.");
            e.printStackTrace();
        }
    }

    public String getConfiguracoesAplicacao(){
        if (repoImplementacao.equals("postgres")){
            return "Repo:" + repoImplementacao + " DB_Host:" +db_host +  " DB_Port:" + db_port  +  " User:" +userDB + " String Conexao:" + stringConexaoBD +" Log: " +logImplementacao;
        }
        else {
            return "Repo:" + repoImplementacao + " Log:" +logImplementacao;
        }
    }

    public static Aplicacao getInstance(){
        if(instance == null){
            synchronized (Aplicacao.class) {
                if(instance == null){
                    instance = new Aplicacao();
                }
            }
        }
        return instance;
    }

    private void configurarRepositorioAplicacao() {
        if (this.repoImplementacao.equals("postgres")){
            this.ativoRepositorio = new AtivoRepositorioImplcomJDBC();
        }
        else {
            this.ativoRepositorio = new AtivoRepositorioImplMemoria();
        }
    }



    private void configurarLogAplicacao(){
        this.logAplicacao = new LogInterfaceImplConsole();
    }



    private void montarListaConfiguracoes() {
        this.propriedadeLog = new ArrayList<>();
        this.propriedadeLog.add("console");
        this.propriedadeRepo = new ArrayList<>();
        this.propriedadeRepo.add("postgres");
        this.propriedadeRepo.add("memoria");
    }

    private void carregarConfiguracoes() throws PropriedadeInvalidaConfigExcecao {
        PropriedadeInvalidaConfigExcecao propriedadeInvalidaConfigExcecao;
        montarListaConfiguracoes();
        carregarConfigBancoDados();
        try {
            Properties properties = new Properties();
            FileInputStream fis = new FileInputStream("config/config.properties");
            properties.load(fis);
            this.logImplementacao = properties.getProperty("log.implementacao");
            this.repoImplementacao = properties.getProperty("repositorio.implementacao");
            this.validarConfiguracoes();
        }
        catch (FileNotFoundException e) {
            propriedadeInvalidaConfigExcecao = new PropriedadeInvalidaConfigExcecao("Não foi possível carregar o arquivo de configuração: config.properties");
            propriedadeInvalidaConfigExcecao.addSuppressed(e);
            throw propriedadeInvalidaConfigExcecao;

        }
        catch (IOException e) {
            propriedadeInvalidaConfigExcecao = new PropriedadeInvalidaConfigExcecao("Não foi possível carregar o arquivo de configuração: config.properties");
            propriedadeInvalidaConfigExcecao.addSuppressed(e);
            throw propriedadeInvalidaConfigExcecao;
        }
    }

    private void validarConfiguracoes() throws PropriedadeInvalidaConfigExcecao {
        if ( ! propriedadeLog.contains(logImplementacao)){
            throw new PropriedadeInvalidaConfigExcecao("Propriedade: log.implementacao inválida.");
        }
        if ( ! propriedadeRepo.contains(repoImplementacao)){
            throw new PropriedadeInvalidaConfigExcecao("Propriedade: repositorio.implementacao inválida.");
        }
    }

    public LogInterface getLoggingInterface() {
        return this.logAplicacao;
    }

    @Override
    public ConexaoInterface getConexaoAplicacao() {
        return ConexaoConfig.getInstance();
    }

    public AtivoRepositorioInterface getAtivoRepositorio() {
        return ativoRepositorio;
    }

    public void setAtivoRepositorio(AtivoRepositorioInterface ativoRepositorio) {
        this.ativoRepositorio = ativoRepositorio;
    }

    public LogInterface gerarNovoObjetoLog() {
        // deve ler parametros de configurar e instanciar a implementação correta de log
        // Por enquanto so tem uma implementacao
        // pode ser usado para o log de cada sessao / requisição
        return new LogInterfaceImplConsole();
    }

    public UUID getUniqueKey(){
        return null;
    }

    @Override
    public boolean healthCheckOk(DataProviderInterface data) {
        try {
            this.getAtivoRepositorio().healthCheck(data);
            this.getLoggingInterface().healthCheck(data);
            return true;
        }
        catch (Exception e) {
            data.getConexaoAplicacao().setConnectionAtiva(false);
            return false;
        }
    }


    private void carregarConfigBancoDados() {

        // Carrega variávieis de ambiente recebidas pelo Docker
        this.db_host = System.getenv("DB_HOST");
        this.db_port = System.getenv("DB_PORT");
        // Se não conseguir carregar variáveis do docker, set variáveis de ambiente de desenvolvimento local
        if (db_host == null) {
            db_host = "localhost"; //ambiente local
        }
        if (db_port == null) {
            db_port = "5433"; // ambiente local
        }
        this.stringConexaoBD = "jdbc:postgresql://" + db_host + ":" + db_port + "/investpess_ativo";
        this.userDB = "postgres";
        this.passwordDB = null;

        // produção - String url = "jdbc:postgresql://db:5432/investpess_ativo";
        // dev - String url = "jdbc:postgresql://localhost:5433/investpess_ativo";
        // dev - String url = "jdbc:postgresql://localhost:5433/investpess_ativo";
    }


    public String getStringConexaoBD() {
        return stringConexaoBD;
    }

    public String getUserDB() {
        return userDB;
    }

    public String getPasswordDB() {
        return passwordDB;
    }



}
