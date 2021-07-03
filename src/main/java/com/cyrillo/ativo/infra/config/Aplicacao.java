package com.cyrillo.ativo.infra.config;

import com.cyrillo.ativo.core.dataprovider.AtivoRepositorioInterface;
import com.cyrillo.ativo.core.dataprovider.DataProviderInterface;
import com.cyrillo.ativo.core.dataprovider.LoggingInterface;
import com.cyrillo.ativo.infra.config.excecao.PropriedadeInvalidaExcecao;
import com.cyrillo.ativo.infra.dataprovider.AtivoRepositorioImplMemoria;
import com.cyrillo.ativo.infra.dataprovider.AtivoRepositorioImplcomJDBC;
import com.cyrillo.ativo.infra.dataprovider.LoggingInterfaceImplConsole;
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
    private LoggingInterface logAplicacao;
    private AtivoRepositorioInterface ativoRepositorio;
    private String RepoImplementacao;
    private String logImplementacao;
    private List<String> propriedadeLog; // lista de todos os domínios possíveis para a propriedade de log.
    private List<String> propriedadeRepo;// lista de todos os domínios possíveis para a propriedade de log.

    private Aplicacao(){
    }

    public void inicializaAplicacao(){
        try {
            montarListaConfiguracoes();
            carregarConfiguracoes();
            configurarLogAplicacao();
            this.logAplicacao.logInfo(null,"Inicializando aplicação...");
            configurarRepositorioAplicacao();
            this.logAplicacao.logInfo(null,"Propriedades de configuração da aplicação carregadas!");
            this.logAplicacao.logInfo(null,getConfiguracoesAplicacao());
            // Cria o servidor GRPC
            AtivoServerGRPC var = new AtivoServerGRPC();
        }
        catch (Exception e){
            System.out.println("Não foi possível inicializar a aplicação.");
            e.printStackTrace();
        }
    }

    public String getConfiguracoesAplicacao(){
        return "Repo: " + RepoImplementacao + " Log: " +logImplementacao ;
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
        if (this.RepoImplementacao.equals("postgres")){
            this.ativoRepositorio = new AtivoRepositorioImplcomJDBC();
        }
        else {
            this.ativoRepositorio = new AtivoRepositorioImplMemoria();
        }
    }



    private void configurarLogAplicacao(){
        this.logAplicacao = new LoggingInterfaceImplConsole();
    }



    private void montarListaConfiguracoes() {
        this.propriedadeLog = new ArrayList<>();
        this.propriedadeLog.add("console");
        this.propriedadeRepo = new ArrayList<>();
        this.propriedadeRepo.add("postgres");
        this.propriedadeRepo.add("memoria");
    }

    private void carregarConfiguracoes() throws PropriedadeInvalidaExcecao  {
        PropriedadeInvalidaExcecao propriedadeInvalidaExcecao;
        try {
            Properties properties = new Properties();
            FileInputStream fis = new FileInputStream("config/config.properties");
            properties.load(fis);
            this.logImplementacao = properties.getProperty("log.implementacao");
            this.RepoImplementacao = properties.getProperty("repositorio.implementacao");
            this.validarConfiguracoes();
        }
        catch (FileNotFoundException e) {
            propriedadeInvalidaExcecao = new PropriedadeInvalidaExcecao("Não foi possível carregar o arquivo de configuração: config.properties");
            propriedadeInvalidaExcecao.addSuppressed(e);
            throw propriedadeInvalidaExcecao;

        }
        catch (IOException e) {
            propriedadeInvalidaExcecao = new PropriedadeInvalidaExcecao("Não foi possível carregar o arquivo de configuração: config.properties");
            propriedadeInvalidaExcecao.addSuppressed(e);
            throw propriedadeInvalidaExcecao;
        }
    }

    private void validarConfiguracoes() throws PropriedadeInvalidaExcecao  {
        if ( ! propriedadeLog.contains(logImplementacao)){
            throw new PropriedadeInvalidaExcecao("Propriedade: log.implementacao inválida.");
        }
        if ( ! propriedadeRepo.contains(RepoImplementacao)){
            throw new PropriedadeInvalidaExcecao("Propriedade: repositorio.implementacao inválida.");
        }
    }

    public LoggingInterface getLoggingInterface() {
        return this.logAplicacao;
    }

    public AtivoRepositorioInterface getAtivoRepositorio() {
        return ativoRepositorio;
    }

    public void setAtivoRepositorio(AtivoRepositorioInterface ativoRepositorio) {
        this.ativoRepositorio = ativoRepositorio;
    }

    public LoggingInterface gerarNovoObjetoLog() {
        // deve ler parametros de configurar e instanciar a implementação correta de log
        // Por enquanto so tem uma implementacao
        // pode ser usado para o log de cada sessao / requisição
        return new LoggingInterfaceImplConsole();
    }

    public UUID getUniqueKey(){
        return null;
    }
}
