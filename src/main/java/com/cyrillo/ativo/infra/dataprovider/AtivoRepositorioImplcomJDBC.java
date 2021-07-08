package com.cyrillo.ativo.infra.dataprovider;

import com.cyrillo.ativo.core.dataprovider.*;
import com.cyrillo.ativo.core.dataprovider.dto.AtivoDto;
import com.cyrillo.ativo.core.dataprovider.excecao.FalhaObterConexaoRepositorioExcecao;
import com.cyrillo.ativo.core.usecase.excecao.ComunicacaoRepositorioException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AtivoRepositorioImplcomJDBC implements AtivoRepositorioInterface {

    public AtivoRepositorioImplcomJDBC(){}


    @Override
    public void incluir(DataProviderInterface data, AtivoDtoInterface ativoObjeto) throws ComunicacaoRepositorioException {
        // Precisa ser refatorado...
        // camada de entrypoint conhecendo camada core entidade.
        // Essa camada deveria conhecer apenas use case

        try {
            LogInterface log = data.getLoggingInterface();
            String uniqueKey =String.valueOf(data.getUniqueKey());

            log.logInfo(uniqueKey,"Iniciando Repositorio que cadastra o ativo.");
            String sigla_ativo = ativoObjeto.getSigla().toUpperCase();
            String nome_ativo = ativoObjeto.getNomeAtivo();
            String descricao_cnpj_ativo = ativoObjeto.getDescricaoCNPJAtivo();
            int tipo_ativo = ativoObjeto.getTipoAtivoInt();

            log.logInfo(uniqueKey,"Dados do ativo identificados.");
            // consulta no banco postgresql

            String sql = "INSERT INTO ativoobjeto (sigla_ativo,nome_ativo,descricao_cnpj_ativo,tipo_ativo)";
            sql = sql + " VALUES ('" + sigla_ativo + "','" + nome_ativo + "','" + descricao_cnpj_ativo + "'," + String.valueOf(tipo_ativo)+")";

            log.logInfo(uniqueKey,"Insert montado, pega conexão da classe singleton.");

            ConexaoInterface conexao = data.getConexaoAplicacao();
            Connection conn = conexao.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.execute();
            log.logInfo(uniqueKey,"Ativo: " + nome_ativo + " cadastrado!");
        }
        catch (SQLException e) {
            LogInterface log = data.getLoggingInterface();
            String uniqueKey =String.valueOf(data.getUniqueKey());
            ConexaoInterface conexao = data.getConexaoAplicacao();
            conexao.setConnectionAtiva(false);
            ComunicacaoRepositorioException falha = new ComunicacaoRepositorioException("Falha na comunicação com Repositório: AtivoRepositorioImplcomJDBC");
            falha.addSuppressed(e);
            log.logError(uniqueKey,"Erro na comunicação com repositório. SQL Exception.");
            throw falha;
        }
        catch (FalhaObterConexaoRepositorioExcecao e) {
            LogInterface log = data.getLoggingInterface();
            String uniqueKey =String.valueOf(data.getUniqueKey());
            ConexaoInterface conexao = data.getConexaoAplicacao();
            conexao.setConnectionAtiva(false);
            ComunicacaoRepositorioException falha = new ComunicacaoRepositorioException("Falha para obter conexao com Repositório.");
            log.logError(uniqueKey,"Erro na comunicação com repositório. FalhaObterConexaoRepositorioExcecao.");
            falha.addSuppressed(e);
            throw falha;
        }
    }

    @Override
    public boolean consultarPorSigla(DataProviderInterface data, String siglaAtivo) throws ComunicacaoRepositorioException {

        try {
            LogInterface log = data.getLoggingInterface();
            String uniqueKey =String.valueOf(data.getUniqueKey());
            log.logInfo(uniqueKey,"Iniciando Repositorio que consulta um ativo pela sigla.");

            // consulta no banco postgresql

            // preciso transforma siglaativo em maiusculo
            siglaAtivo = siglaAtivo.toUpperCase();
            String sql = "SELECT count(sigla_ativo) as qtd_ativos FROM ativoobjeto WHERE sigla_ativo='" + siglaAtivo + "'";

            log.logInfo(uniqueKey,"Select montado, pega conexão da classe singleton");

            ConexaoInterface conexao = data.getConexaoAplicacao();
            Connection conn = conexao.getConnection();
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
            LogInterface log = data.getLoggingInterface();
            String uniqueKey =String.valueOf(data.getUniqueKey());
            log.logError(uniqueKey,"SQL Exception no banco.");
            ConexaoInterface conexao = data.getConexaoAplicacao();
            conexao.setConnectionAtiva(false);
            ComunicacaoRepositorioException falha = new ComunicacaoRepositorioException("Falha na comunicação com Repositório: AtivoRepositorioImplcomJDBC");
            falha.addSuppressed(e);
            throw falha;
        }
        catch (FalhaObterConexaoRepositorioExcecao e) {
            LogInterface log = data.getLoggingInterface();
            String uniqueKey =String.valueOf(data.getUniqueKey());
            log.logError(uniqueKey,"Falha em buscar conexão com o repositório.");
            ConexaoInterface conexao = data.getConexaoAplicacao();
            conexao.setConnectionAtiva(false);
            ComunicacaoRepositorioException falha = new ComunicacaoRepositorioException("Falha para obter conexao com Repositório.");
            falha.addSuppressed(e);
            throw falha;
        }
    }
    @Override
    public List<AtivoDtoInterface> listarTodosAtivos(DataProviderInterface data) {
        return null;
    }

    @Override
    public List<AtivoDtoInterface> listarAtivosPorTipo(DataProviderInterface data, int tipoAtivo) throws ComunicacaoRepositorioException {
        String siglaAtivo;
        String nomeAtivo;
        String cnpjAtivo;
        List<AtivoDtoInterface> listaAtivoObjeto = new ArrayList<>();

        try {
            LogInterface log = data.getLoggingInterface();
            String uniqueKey =String.valueOf(data.getUniqueKey());
            log.logInfo(uniqueKey,"Iniciando Repositorio que consulta um ativo pela sigla.");

            String sql = "SELECT sigla_ativo,nome_ativo,descricao_cnpj_ativo FROM ativoobjeto WHERE tipo_ativo = ?";
            log.logInfo(uniqueKey,"Select montado, pega conexão da classe singleton");

            ConexaoInterface conexao = data.getConexaoAplicacao();
            Connection conn = conexao.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1,tipoAtivo);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                siglaAtivo = rs.getString("sigla_ativo");
                nomeAtivo = rs.getString("nome_ativo");
                cnpjAtivo = rs.getString("descricao_cnpj_ativo");
                AtivoDto ativoObjeto = new AtivoDto(siglaAtivo,nomeAtivo,cnpjAtivo,tipoAtivo);
                listaAtivoObjeto.add(ativoObjeto);
            }
            rs.close();
            return listaAtivoObjeto;
        }
        catch (SQLException e){
            LogInterface log = data.getLoggingInterface();
            String uniqueKey =String.valueOf(data.getUniqueKey());
            log.logError(uniqueKey,"SQL Exception no banco.");
            ConexaoInterface conexao = data.getConexaoAplicacao();
            conexao.setConnectionAtiva(false);
            ComunicacaoRepositorioException falha = new ComunicacaoRepositorioException("Falha na comunicação com Repositório: AtivoRepositorioImplcomJDBC");
            falha.addSuppressed(e);
            throw falha;
        }
        catch (FalhaObterConexaoRepositorioExcecao e) {
            LogInterface log = data.getLoggingInterface();
            String uniqueKey =String.valueOf(data.getUniqueKey());
            log.logError(uniqueKey,"Falha em buscar conexão com o repositório.");
            ConexaoInterface conexao = data.getConexaoAplicacao();
            conexao.setConnectionAtiva(false);
            ComunicacaoRepositorioException falha = new ComunicacaoRepositorioException("Falha para obter conexao com Repositório.");
            falha.addSuppressed(e);
            throw falha;
        }
    }
}
