package com.cyrillo.ativo.infra.dataprovider;

import com.cyrillo.ativo.infra.config.ConexaoConfig;
import com.cyrillo.ativo.core.entidade.AtivoObjeto;
import com.cyrillo.ativo.core.entidade.TipoAtivo;
import com.cyrillo.ativo.core.dataprovider.AtivoRepositorioInterface;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class AtivoRepositorioImplcomJDBC implements AtivoRepositorioInterface {

    public AtivoRepositorioImplcomJDBC(){}


    @Override
    public int incluir(AtivoObjeto ativoObjeto) {
        // Mapa de resultados do Repositorio
        // 0 -> sucesso
        // 1 -> Erro na comunicação com serviço de persistencia


        String msgErro = "Ativo cadastrado com sucesso.";
        int resultado = 0;
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
            resultado = 1;
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
    public List<AtivoObjeto> listarAtivosPorTipo(TipoAtivo tipoAtivo) {
        return null;
    }
}
