package com.cyrillo.ativo.infra.entrypoint.servicogrpc;

import com.cyrillo.ativo.core.dataprovider.tipos.LoggingInterface;
import com.cyrillo.ativo.core.entidade.excecao.FalhaComunicacaoRepositorioException;
import com.cyrillo.ativo.core.usecase.ListarAtivosPorTipo;
import com.cyrillo.ativo.infra.config.Sessao;
import com.cyrillo.ativo.infra.entrypoint.servicogrpc.ativoobjetoproto.ConsultaListaAtivoRequest;
import com.cyrillo.ativo.infra.entrypoint.servicogrpc.ativoobjetoproto.ConsultaListaAtivoResponse;
import com.cyrillo.ativo.infra.entrypoint.servicogrpc.ativoobjetoproto.ConsultaListaAtivoServiceGrpc;
import io.grpc.stub.StreamObserver;

import java.util.UUID;

public class ConsultaListaAtivoService extends ConsultaListaAtivoServiceGrpc.ConsultaListaAtivoServiceImplBase {
    @Override
    public void consultaListaAtivo(ConsultaListaAtivoRequest request, StreamObserver<ConsultaListaAtivoResponse> responseObserver) {
        // 101 - Já existe ativo com essa sigla
        // 201 - Ativo cadastrado com sucesso
        // 401 - Falha técnica na inclusão de um ativo

        String msgResultado;
        int codResultado;

        Sessao dataProvider = new Sessao();
        UUID uniqueKey = dataProvider.getUniqueKey();
        LoggingInterface log = dataProvider.getLoggingInterface();

        //
        //message ConsultaListaAtivoRequest {
        //    string sigla_ativo = 1;
        //}

        //message ConsultaListaAtivoResponse {
        //    int32 response_code = 1;
        //    string response_message = 2;
        //    repeated AtivoObjeto ativos = 3;
       // }

        log.logInfo(String.valueOf(uniqueKey),"Iniciando consulta de ativos");
        int tipoAtivo = request.getTipoAtivo();

        log.logInfo(String.valueOf(uniqueKey),"Dados da Request de Lista de Ativos identificados");

        try {
            ListarAtivosPorTipo listarAtivosPorTipo = new ListarAtivosPorTipo();
            listarAtivosPorTipo.executar(dataProvider,tipoAtivo);
            codResultado = 200;
            msgResultado = "Lissta ok";
        }
        catch (FalhaComunicacaoRepositorioException e) {
            codResultado = 401;
            msgResultado = "Erro na persistência do Ativo no banco de dados!";
        }
        catch(Exception e){
            codResultado = 500;
            msgResultado = "Erro não identificado" + e.getMessage();
        }

        // List<String> list = new ArrayList<String>();
        //    list.add("a string");
        //
        //    Iterable<String> iterable = list;
        //    for (String s : iterable) {
        //        System.out.println(s);
        ConsultaListaAtivoResponse response = ConsultaListaAtivoResponse.newBuilder()
                //.addAllAtivos()
                .setResponseCode(codResultado)
                .setResponseMessage(msgResultado)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}


