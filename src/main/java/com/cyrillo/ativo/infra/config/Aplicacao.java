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
import java.net.MalformedURLException;
import io.jaegertracing.Configuration;
import io.jaegertracing.Configuration.ReporterConfiguration;
import io.jaegertracing.Configuration.SamplerConfiguration;
import io.jaegertracing.Configuration.SenderConfiguration;
import io.jaegertracing.internal.samplers.ConstSampler;
import io.opentracing.Tracer;
import io.opentracing.contrib.grpc.TracingServerInterceptor;
import io.opentracing.util.GlobalTracer;


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
    private int defaultTimeoutRepo;
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
    private String jaeger_reporter_host;
    private int jaeger_reporter_port;
    private String opentracing_jaeger_service_name;
    private ServidorTracingDistribuido servidorTracingDistribuido;


    private Aplicacao(){
    }

    public void inicializaAplicacao(){
        try {
            carregarConfiguracoes();
            // Configura Data Providers (Aplicação e Repositório)
            configurarLogAplicacao();
            this.logAplicacao.logInfo(null,"Inicializando aplicação...");
            configurarRepositorioAplicacao();
            this.logAplicacao.logInfo(null,"Propriedades de configuração da aplicação carregadas!");
            this.logAplicacao.logInfo(null,getConfiguracoesAplicacao());
            configurarTracerGlobalJaeger(jaeger_reporter_host, Integer.toString(jaeger_reporter_port), opentracing_jaeger_service_name);
            // Levanta o servidor de tracing distribuido
            this.servidorTracingDistribuido = new ServidorTracingDistribuido(this);
            // Levanta o servidor GRPC
            AtivoServerGRPC var = new AtivoServerGRPC(this);

        }
        catch (Exception e){
            System.out.println("Não foi possível inicializar a aplicação.");
            e.printStackTrace();
        }
    }

    public String getConfiguracoesAplicacao(){
        String descricaoConfiguracaoAplicacao;
        if (repoImplementacao.equals("postgres")){
            descricaoConfiguracaoAplicacao = "Repo:" + repoImplementacao + " DB_Host:" +db_host +  " DB_Port:" + db_port  +  " User:" +userDB + " String Conexao:" + stringConexaoBD +" Log: " +logImplementacao;
            descricaoConfiguracaoAplicacao = descricaoConfiguracaoAplicacao + " Jaeger Host:" + jaeger_reporter_host + " Jaeger Port:" + jaeger_reporter_port;
        }
        else {
            descricaoConfiguracaoAplicacao = "Repo:" + repoImplementacao + " Log:" +logImplementacao + " Jaeger Host:" + jaeger_reporter_host + " Jaeger Port:" + jaeger_reporter_port;
        }
        return descricaoConfiguracaoAplicacao;
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
            this.defaultTimeoutRepo = Integer.parseInt(properties.getProperty("repositorio.timeout"));
            //this.jaeger_reporter_host = properties.getProperty("jaeger.reporter_host");
            this.jaeger_reporter_port = Integer.parseInt(properties.getProperty("jaeger.reporter_port"));
            this.opentracing_jaeger_service_name = properties.getProperty("opentracing.jaeger.service-name");
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
        catch (Exception e) {
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
        if ( defaultTimeoutRepo <=0 ){
            throw new PropriedadeInvalidaConfigExcecao("Propriedade: repositorio.timeout inválida.");
        }

        if ( jaeger_reporter_port <=0 ){
            throw new PropriedadeInvalidaConfigExcecao("Propriedade: jaeger.reporter_port inválida.");
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

    @Override
    public int getTimeOutDefault() {
        return defaultTimeoutRepo;
    }


    private void carregarConfigBancoDados() {

        // Carrega variávieis de ambiente recebidas pelo Docker
        this.db_host = System.getenv("DB_HOST");
        this.db_port = System.getenv("DB_PORT");
        this.jaeger_reporter_host = System.getenv("JAEGER_HOST");
        // Se não conseguir carregar variáveis do docker, set variáveis de ambiente de desenvolvimento local
        if (db_host == null) {
            db_host = "localhost"; //ambiente local
        }

        if (db_port == null) {
            db_port = "5433"; // ambiente local
        }

        if (jaeger_reporter_host == null) {
            jaeger_reporter_host = "localhost"; //ambiente local
        }


        this.stringConexaoBD = "jdbc:postgresql://" + db_host + ":" + db_port + "/investpess_ativo";
        this.userDB = "postgres";
        this.passwordDB = null;

        // produção - String url = "jdbc:postgresql://db:5432/investpess_ativo";
        // dev - String url = "jdbc:postgresql://localhost:5433/investpess_ativo";
        // dev - String url = "jdbc:postgresql://localhost:5433/investpess_ativo";
    }

    static void configurarTracerGlobalJaeger(String jaeger_reporter_host, String jaeger_reporter_port, String opentracing_jaeger_service_name ) throws MalformedURLException
    {
        Tracer tracer = null;
        SamplerConfiguration samplerConfig = new SamplerConfiguration()
                    .withType(ConstSampler.TYPE)
                    .withParam(1);
        SenderConfiguration senderConfig = new SenderConfiguration()
                    .withAgentHost(jaeger_reporter_host)
                    .withAgentPort(Integer.decode(jaeger_reporter_port));
        ReporterConfiguration reporterConfig = new ReporterConfiguration()
                    .withLogSpans(true)
                    .withFlushInterval(1000)
                    .withMaxQueueSize(10000)
                    .withSender(senderConfig);
        tracer = new Configuration(opentracing_jaeger_service_name).withSampler(samplerConfig).withReporter(reporterConfig).getTracer();
        GlobalTracer.registerIfAbsent(tracer);
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

    public DataProviderInterface geraSessao(){
        return new Sessao();
    }

    public String getJaeger_reporter_host() {
        return jaeger_reporter_host;
    }

    public int getJaeger_reporter_port() {
        return jaeger_reporter_port;
    }
    public String getOpentracing_jaeger_service_name() {
        return opentracing_jaeger_service_name;
    }

    public TracingServerInterceptor geTtracingServerInterceptor() {
        return this.servidorTracingDistribuido.geTtracingServerInterceptor();
    }

}
