package com.cyrillo.ativo.infra.entrypoint.servicogrpc;

import com.cyrillo.ativo.core.usecase.IncluirNovoAtivo;
import com.cyrillo.ativo.infra.dataprovider.AtivoRepositorioImplcomJDBC;
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

        // deveria pedir o repositorio dinamico para o config. Precisa refatorar.

        // Instancia repositorio
        AtivoRepositorioImplcomJDBC ativoRepositorieImplcomJDBC = new AtivoRepositorioImplcomJDBC();
        // Instancia e executa caso de uso
        IncluirNovoAtivo incluirNovoAtivo = new IncluirNovoAtivo();
        int resultado = incluirNovoAtivo.executar(ativoRepositorieImplcomJDBC,sigla_ativo,nome_ativo,descricao_cnpj_ativo,tipo_ativo);

        String msgresultado;
        if (resultado == 0) {
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