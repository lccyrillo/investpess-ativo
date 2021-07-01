package com.cyrillo.ativo.infra.dataprovider;

import com.cyrillo.ativo.core.dataprovider.AtivoRepositorioInterface;
import com.cyrillo.ativo.core.entidade.AtivoObjeto;
import com.cyrillo.ativo.core.entidade.TipoAtivo;
import com.cyrillo.ativo.infra.config.ConexaoConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class AtivoRepositorioImplcomJDBC implements AtivoRepositorioInterface {

    public AtivoRepositorioImplcomJDBC(){}


    @Override
    public void incluir(AtivoObjeto ativoObjeto) throws SQLException {
        // Mapa de resultados do Repositorio
        // 200 -> sucesso
        // 500 -> Erro na comunicação com serviço de persistencia

        //int resultado = 200; //
        //int classeResultado = 2; // Resultado esperado ok.
        //String msgResultado = "Ativo cadastrado com sucesso.";
        //String msgStack = null;

        String sigla_ativo = ativoObjeto.getSigla();
        String nome_ativo = ativoObjeto.getNomeAtivo();
        String descricao_cnpj_ativo = ativoObjeto.getDescricaoCNPJAtivo();
        int tipo_ativo = ativoObjeto.getTipoAtivo().getTipoAtivo();

        System.out.println("Dados do ativo identifocados");
        // consulta no banco postgresql

        System.out.println("Montou insert");
        String sql = "INSERT INTO ativoobjeto (sigla_ativo,nome_ativo,descricao_cnpj_ativo,tipo_ativo)";
        sql = sql + " VALUES ('" + sigla_ativo + "','" + nome_ativo + "','" + descricao_cnpj_ativo + "'," + String.valueOf(tipo_ativo)+")";


        System.out.println("Pega conexão da classe singleton");
        Connection conn = ConexaoConfig.getInstance().getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.execute();
        System.out.println("executou sql");

        System.out.println("executou sql");
        System.out.println("Ativo: " + nome_ativo + " cadastrado!");


    }

    @Override
    public AtivoObjeto consultarPorSigla(String siglaAtivo) {
        return null;
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
