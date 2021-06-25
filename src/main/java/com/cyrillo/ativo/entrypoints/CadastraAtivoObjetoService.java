package com.cyrillo.ativo.entrypoints;

import com.cyrillo.ativo.configuration.ConexaoConfig;
import com.proto.ativo.ativoobjetoproto.AtivoObjeto;
import com.proto.ativo.ativoobjetoproto.CadastraAtivoObjetoRequest;
import com.proto.ativo.ativoobjetoproto.CadastraAtivoObjetoResponse;
import com.proto.ativo.ativoobjetoproto.CadastraAtivoObjetoServiceGrpc;
import io.grpc.stub.StreamObserver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CadastraAtivoObjetoService  extends CadastraAtivoObjetoServiceGrpc.CadastraAtivoObjetoServiceImplBase {
    @Override
    public void cadastraAtivoObjeto(CadastraAtivoObjetoRequest request, StreamObserver<CadastraAtivoObjetoResponse> responseObserver) {
        // super.cadastraAtivoObjeto(request, responseObserver);
        String msgErro = "sem erro";
        System.out.println("iniciando cadastra ativo objeto");
        AtivoObjeto ativo = request.getAtivo();
        String sigla_ativo = ativo.getSiglaAtivo();
        String nome_ativo = ativo.getNomeAtivo();
        String descricao_cnpj_ativo = ativo.getDescricaoCnpjAtivo();
        int tipo_ativo = ativo.getTipoAtivo();

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
            throwables.printStackTrace();
            msgErro = throwables.getMessage();
        }



        String result = "Ativo: " + nome_ativo + " cadastrado!" + msgErro;
        CadastraAtivoObjetoResponse response = CadastraAtivoObjetoResponse.newBuilder()
                .setResponseCode("200")
                .setResponseMessage(result)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}