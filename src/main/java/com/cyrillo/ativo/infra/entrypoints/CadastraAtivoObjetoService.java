package com.cyrillo.ativo.infra.entrypoints;

import com.cyrillo.ativo.entities.AtivoObjeto;
import com.cyrillo.ativo.entities.AtivoObjetoBuilder;
import com.cyrillo.ativo.infra.dataprovider.AtivoRepositorieImplcomJDBC;
import io.grpc.stub.StreamObserver;
import proto.ativo.ativoobjetoproto.CadastraAtivoObjetoRequest;
import proto.ativo.ativoobjetoproto.CadastraAtivoObjetoResponse;
import proto.ativo.ativoobjetoproto.CadastraAtivoObjetoServiceGrpc;

public class CadastraAtivoObjetoService  extends CadastraAtivoObjetoServiceGrpc.CadastraAtivoObjetoServiceImplBase {
    @Override
    public void cadastraAtivoObjeto(CadastraAtivoObjetoRequest request, StreamObserver<CadastraAtivoObjetoResponse> responseObserver) {
        // super.cadastraAtivoObjeto(request, responseObserver);
        System.out.println("iniciando cadastra ativo objeto");
        proto.ativo.ativoobjetoproto.AtivoObjeto ativo = request.getAtivo();
        String sigla_ativo = ativo.getSiglaAtivo();
        String nome_ativo = ativo.getNomeAtivo();
        String descricao_cnpj_ativo = ativo.getDescricaoCnpjAtivo();
        int tipo_ativo = ativo.getTipoAtivo();

        System.out.println("Dados do ativo identifocados");
        // consulta no banco postgresql

        AtivoRepositorieImplcomJDBC ativoRepositorieImplcomJDBC = new AtivoRepositorieImplcomJDBC();
        AtivoObjetoBuilder builderAtivo = new AtivoObjetoBuilder();
        builderAtivo.infoCompleta(sigla_ativo,nome_ativo,descricao_cnpj_ativo,tipo_ativo);
        AtivoObjeto ativoObjeto = builderAtivo.build();

        int resultado = ativoRepositorieImplcomJDBC.incluir(ativoObjeto);
        String msgresultado;
        if (resultado == 200) {
            msgresultado = "Ativo: " + nome_ativo + " cadastrado!";
        }
        else {
            msgresultado = "Erro ao cadastrar o ativo. Erro: " +  resultado;
        }
        CadastraAtivoObjetoResponse response = CadastraAtivoObjetoResponse.newBuilder()
                .setResponseCode("200")
                .setResponseMessage(msgresultado)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}