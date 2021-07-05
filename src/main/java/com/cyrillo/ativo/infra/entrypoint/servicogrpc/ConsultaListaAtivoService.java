package com.cyrillo.ativo.infra.entrypoint.servicogrpc;

import com.cyrillo.ativo.core.dataprovider.tipos.AtivoDtoInterface;
import com.cyrillo.ativo.core.dataprovider.tipos.LoggingInterface;
import com.cyrillo.ativo.core.entidade.excecao.FalhaComunicacaoRepositorioException;
import com.cyrillo.ativo.core.usecase.ListarAtivosPorTipo;
import com.cyrillo.ativo.infra.config.Sessao;
import com.cyrillo.ativo.infra.entrypoint.servicogrpc.ativoobjetoproto.AtivoObjeto;
import com.cyrillo.ativo.infra.entrypoint.servicogrpc.ativoobjetoproto.ConsultaListaAtivoRequest;
import com.cyrillo.ativo.infra.entrypoint.servicogrpc.ativoobjetoproto.ConsultaListaAtivoResponse;
import com.cyrillo.ativo.infra.entrypoint.servicogrpc.ativoobjetoproto.ConsultaListaAtivoServiceGrpc;
import io.grpc.stub.StreamObserver;

import java.util.List;

public class ConsultaListaAtivoService extends ConsultaListaAtivoServiceGrpc.ConsultaListaAtivoServiceImplBase {
    @Override
    public void consultaListaAtivo(ConsultaListaAtivoRequest request, StreamObserver<ConsultaListaAtivoResponse> responseObserver) {
        // 101 - Já existe ativo com essa sigla
        // 201 - Ativo cadastrado com sucesso
        // 401 - Falha técnica na inclusão de um ativo

        String msgResultado;
        int codResultado;
        boolean montarLista = false;

        Sessao dataProvider = new Sessao();
        String uniqueKey = String.valueOf(dataProvider.getUniqueKey());
        LoggingInterface log = dataProvider.getLoggingInterface();
        List<AtivoDtoInterface> lista = null;
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
            lista = listarAtivosPorTipo.executar(dataProvider,tipoAtivo);
            codResultado = 200;
            msgResultado = "Lista ok";
            // Preciso montar o retorno repeated
            if (lista != null) {
                montarLista = true;
            }

        }
        catch (FalhaComunicacaoRepositorioException e) {
            codResultado = 401;
            msgResultado = "Erro na comunicação com o repositório de dados!";
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
        ConsultaListaAtivoResponse.Builder builder = ConsultaListaAtivoResponse.newBuilder()
                .setResponseCode(codResultado)
                .setResponseMessage(msgResultado);
        if (montarLista == true) {
            for (int i = 0; i < lista.size(); i++) {
                AtivoObjeto ativoObjeto = AtivoObjeto.newBuilder().
                        setTipoAtivo(lista.get(i).getTipoAtivoInt()).
                        setDescricaoCnpjAtivo(lista.get(i).getDescricaoCNPJAtivo()).
                        setNomeAtivo(lista.get(i).getNomeAtivo()).
                        setSiglaAtivo(lista.get(i).getSigla()).
                        build();
                builder.addAtivos(ativoObjeto);
            }
        }
        ConsultaListaAtivoResponse response = builder.build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
