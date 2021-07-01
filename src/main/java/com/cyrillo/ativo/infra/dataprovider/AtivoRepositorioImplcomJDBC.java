package com.cyrillo.ativo.infra.dataprovider;

import com.cyrillo.ativo.core.dataprovider.AtivoRepositorioInterface;
import com.cyrillo.ativo.core.dataprovider.LoggingInterface;
import com.cyrillo.ativo.core.entidade.AtivoObjeto;
import com.cyrillo.ativo.core.entidade.TipoAtivo;
import com.cyrillo.ativo.core.usecase.IdentificarLogginInterface;
import com.cyrillo.ativo.infra.config.ConexaoConfig;

import java.sql.*;
import java.util.List;

public class AtivoRepositorioImplcomJDBC implements AtivoRepositorioInterface {

    public AtivoRepositorioImplcomJDBC(){}


    @Override
    public void incluir(AtivoObjeto ativoObjeto) throws SQLException {
        // Precisa ser refatorado...
        // camada de entrypoint conhecendo camada core entidade.
        // Essa camada deveria conhecer apenas use case

        IdentificarLogginInterface identificarLogginInterface = new IdentificarLogginInterface();
        LoggingInterface loggingInterface = identificarLogginInterface.getLoggingInterface();

        loggingInterface.logInfo(null,"Iniciando Repositorio que cadastra o ativo.");
        String sigla_ativo = ativoObjeto.getSigla().toUpperCase();
        String nome_ativo = ativoObjeto.getNomeAtivo();
        String descricao_cnpj_ativo = ativoObjeto.getDescricaoCNPJAtivo();
        int tipo_ativo = ativoObjeto.getTipoAtivo().getTipoAtivo();

        loggingInterface.logInfo(null,"Dados do ativo identifocados");
        // consulta no banco postgresql

        String sql = "INSERT INTO ativoobjeto (sigla_ativo,nome_ativo,descricao_cnpj_ativo,tipo_ativo)";
        sql = sql + " VALUES ('" + sigla_ativo + "','" + nome_ativo + "','" + descricao_cnpj_ativo + "'," + String.valueOf(tipo_ativo)+")";

        loggingInterface.logInfo(null,"Insert montado, pega conexão da classe singleton");

        Connection conn = ConexaoConfig.getInstance().getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.execute();
        loggingInterface.logInfo(null,"Executou o sql");
        loggingInterface.logInfo(null,"Ativo: " + nome_ativo + " cadastrado!");
    }

    @Override
    public boolean consultarPorSigla(String siglaAtivo) throws SQLException {
        IdentificarLogginInterface identificarLogginInterface = new IdentificarLogginInterface();
        LoggingInterface loggingInterface = identificarLogginInterface.getLoggingInterface();

        loggingInterface.logInfo(null,"Iniciando Repositorio que consulta um ativo pela sigla.");

        // consulta no banco postgresql

        // preciso transforma siglaativo em maiusculo
        siglaAtivo = siglaAtivo.toUpperCase();
        String sql = "SELECT count(sigla_ativo) as qtd_ativos FROM ativoobjeto WHERE sigla_ativo='" + siglaAtivo + "'";


        loggingInterface.logInfo(null,"Select montado, pega conexão da classe singleton");

        Connection conn = ConexaoConfig.getInstance().getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        rs.next();
        if (rs.getInt("qtd_ativos") >=1) {
            loggingInterface.logInfo(null,"Já existe essa sigla no banco de dados");
            return true;
        }
        else {
            loggingInterface.logInfo(null,"Não existe essa sigla no banco de dados");
            return false;
        }
    }

    @Override
    public List<AtivoObjeto> listarTodosAtivos() {
        return null;
    }

    @Override
    public List<AtivoObjeto> listarAtivosPorTipo(TipoAtivo tipoAtivo) {
        return null;
    }
}
