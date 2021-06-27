package com.cyrillo.ativo.infra.dataprovider;

import com.cyrillo.ativo.configuration.ConexaoConfig;
import com.cyrillo.ativo.entities.AtivoObjeto;
import com.cyrillo.ativo.entities.TipoAtivo;
import com.cyrillo.ativo.usecases.repositories.AtivoRepositorie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class AtivoRepositorieImplcomJDBC implements AtivoRepositorie {

    public AtivoRepositorieImplcomJDBC(){}


    @Override
    public int incluir(AtivoObjeto ativoObjeto) {
        String msgErro = "Ativo cadastrado com sucesso.";
        int resultado = 200;
        String sigla_ativo = ativoObjeto.getSigla();
        String nome_ativo = ativoObjeto.getNomeAtivo();
        String descricao_cnpj_ativo = ativoObjeto.getDescricaoCNPJAtivo();
        int tipo_ativo = ativoObjeto.getTipoAtivo().getTipoAtivo();

        System.out.println("Dados do ativo identifocados");
        // consulta no banco postgresql

        try {

            System.out.println("Montou insert");
            String sql = "INSERT INTO ativoobjeto (sigla_ativo,nome_ativo,descricao_cnpj_ativo,tipo_ativo)";
            sql = sql + " VALUES ('" + sigla_ativo + "','" + nome_ativo + "','" + descricao_cnpj_ativo + "'," + String.valueOf(tipo_ativo)+")";


            System.out.println("Pega conexão da classe singleton");
            Connection conn = ConexaoConfig.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.execute();
            System.out.println("executou sql");

        } catch (SQLException throwables) {
            //throwables.printStackTrace();
            msgErro = throwables.getMessage();
            resultado = 400;
            System.out.println(msgErro);
        }
        System.out.println("executou sql");
        System.out.println("Ativo: " + nome_ativo + " cadastrado!");
        return resultado;
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
    public List<AtivoObjeto> listarAtivosPorSigla(TipoAtivo tipoAtivo) {
        return null;
    }
}
