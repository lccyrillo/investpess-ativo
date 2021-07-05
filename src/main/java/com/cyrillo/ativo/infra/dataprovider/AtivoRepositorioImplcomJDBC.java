package com.cyrillo.ativo.infra.dataprovider;

import com.cyrillo.ativo.core.dataprovider.tipos.AtivoDtoInterface;
import com.cyrillo.ativo.core.dataprovider.tipos.AtivoRepositorioInterface;
import com.cyrillo.ativo.core.dataprovider.tipos.DataProviderInterface;
import com.cyrillo.ativo.core.dataprovider.tipos.LoggingInterface;
import com.cyrillo.ativo.core.entidade.excecao.FalhaComunicacaoRepositorioException;
import com.cyrillo.ativo.infra.config.Aplicacao;
import com.cyrillo.ativo.infra.config.ConexaoConfig;
import com.cyrillo.ativo.infra.config.excecao.FalhaObterConexaoRepositorioExcecao;

import java.sql.*;
import java.util.List;

public class AtivoRepositorioImplcomJDBC implements AtivoRepositorioInterface {

    public AtivoRepositorioImplcomJDBC(){}


    @Override
    public void incluir(DataProviderInterface data, AtivoDtoInterface ativoObjeto) throws FalhaComunicacaoRepositorioException {
        // Precisa ser refatorado...
        // camada de entrypoint conhecendo camada core entidade.
        // Essa camada deveria conhecer apenas use case

        try {
            LoggingInterface loggingInterface = Aplicacao.getInstance().getLoggingInterface();

            loggingInterface.logInfo(null,"Iniciando Repositorio que cadastra o ativo.");
            String sigla_ativo = ativoObjeto.getSigla().toUpperCase();
            String nome_ativo = ativoObjeto.getNomeAtivo();
            String descricao_cnpj_ativo = ativoObjeto.getDescricaoCNPJAtivo();
            int tipo_ativo = ativoObjeto.getTipoAtivoInt();

            loggingInterface.logInfo(null,"Dados do ativo identifocados");
            // consulta no banco postgresql

            String sql = "INSERT INTO ativoobjeto (sigla_ativo,nome_ativo,descricao_cnpj_ativo,tipo_ativo)";
            sql = sql + " VALUES ('" + sigla_ativo + "','" + nome_ativo + "','" + descricao_cnpj_ativo + "'," + String.valueOf(tipo_ativo)+")";

            //String sql = "INSERT INTO ativoobjeto (sigla_ativo,nome_ativo,descricao_cnpj_ativo,tipo_ativo) VALUES (?,?,?,?)";
            //PreparedStatement ps = conn.prepareStatement(sql);
            //ps.setString(1,sigla_ativo);
            //ps.execute();

            loggingInterface.logInfo(null,"Insert montado, pega conexão da classe singleton");

            ConexaoConfig config =ConexaoConfig.getInstance();
            Connection conn = config.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.execute();
            loggingInterface.logInfo(null,"Executou o sql");
            loggingInterface.logInfo(null,"Ativo: " + nome_ativo + " cadastrado!");
        }
        catch (SQLException e) {
            ConexaoConfig.getInstance().setConexaoAtiva(false);
            FalhaComunicacaoRepositorioException falha = new FalhaComunicacaoRepositorioException("Falha na comunicação com Repositório: AtivoRepositorioImplcomJDBC");
            falha.addSuppressed(e);
            throw falha;
        }
        catch (FalhaObterConexaoRepositorioExcecao e) {
            ConexaoConfig.getInstance().setConexaoAtiva(false);
            FalhaComunicacaoRepositorioException falha = new FalhaComunicacaoRepositorioException("Falha para obter conexao com Repositório.");
            falha.addSuppressed(e);
            throw falha;
        }
    }

    @Override
    public boolean consultarPorSigla(DataProviderInterface data, String siglaAtivo) throws FalhaComunicacaoRepositorioException {

        try {
            LoggingInterface log = data.getLoggingInterface();
            String uniqueKey =String.valueOf(data.getUniqueKey());
            log.logInfo(uniqueKey,"Iniciando Repositorio que consulta um ativo pela sigla.");

            // consulta no banco postgresql

            // preciso transforma siglaativo em maiusculo
            siglaAtivo = siglaAtivo.toUpperCase();
            String sql = "SELECT count(sigla_ativo) as qtd_ativos FROM ativoobjeto WHERE sigla_ativo='" + siglaAtivo + "'";

            log.logInfo(uniqueKey,"Select montado, pega conexão da classe singleton");

            ConexaoConfig config= ConexaoConfig.getInstance();
            Connection conn = config.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            int qtdAtivos = rs.getInt("qtd_ativos");
            rs.close();
            if (qtdAtivos >=1) {
                log.logInfo(uniqueKey,"Já existe essa sigla no banco de dados");
                return true;
            }
            else {
                log.logInfo(uniqueKey,"Não existe essa sigla no banco de dados");
                return false;
            }
        }
        catch (SQLException e){
            LoggingInterface log = data.getLoggingInterface();
            String uniqueKey =String.valueOf(data.getUniqueKey());
            log.logError(uniqueKey,"SQL Exception no banco.");
            ConexaoConfig.getInstance().setConexaoAtiva(false);
            FalhaComunicacaoRepositorioException falha = new FalhaComunicacaoRepositorioException("Falha na comunicação com Repositório: AtivoRepositorioImplcomJDBC");
            falha.addSuppressed(e);
            throw falha;
        }
        catch (FalhaObterConexaoRepositorioExcecao e) {
            LoggingInterface log = data.getLoggingInterface();
            String uniqueKey =String.valueOf(data.getUniqueKey());
            log.logError(uniqueKey,"Falha em buscar conexão com o repositório.");
            ConexaoConfig.getInstance().setConexaoAtiva(false);
            FalhaComunicacaoRepositorioException falha = new FalhaComunicacaoRepositorioException("Falha para obter conexao com Repositório.");
            falha.addSuppressed(e);
            throw falha;
        }
    }
    @Override
    public List<AtivoDtoInterface> listarTodosAtivos(DataProviderInterface data) {
        return null;
    }

    @Override
    public List<AtivoDtoInterface> listarAtivosPorTipo(DataProviderInterface data, int tipoAtivo) throws FalhaComunicacaoRepositorioException{
        FalhaComunicacaoRepositorioException falha = new FalhaComunicacaoRepositorioException("Falha na comunicação com Repositório: AtivoRepositorioImplcomJDBC");
        throw falha;
    }
}
