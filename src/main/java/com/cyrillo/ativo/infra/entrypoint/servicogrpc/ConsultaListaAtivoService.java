package com.cyrillo.ativo.infra.entrypoint.servicogrpc;

import com.cyrillo.ativo.core.dataprovider.AtivoDtoInterface;
import com.cyrillo.ativo.core.dataprovider.LogInterface;
import com.cyrillo.ativo.core.usecase.excecao.ComunicacaoRepositorioException;
import com.cyrillo.ativo.core.usecase.ListarAtivosPorTipo;
import com.cyrillo.ativo.infra.config.Sessao;
import com.cyrillo.ativo.infra.entrypoint.servicogrpc.ativoobjetoproto.AtivoObjeto;
import com.cyrillo.ativo.infra.entrypoint.servicogrpc.ativoobjetoproto.ConsultaListaAtivoRequest;
import com.cyrillo.ativo.infra.entrypoint.servicogrpc.ativoobjetoproto.ConsultaListaAtivoResponse;
import com.cyrillo.ativo.infra.entrypoint.servicogrpc.ativoobjetoproto.ConsultaListaAtivoServiceGrpc;
import io.grpc.stub.StreamObserver;

import java.util.*;

public class ConsultaListaAtivoService extends ConsultaListaAtivoServiceGrpc.ConsultaListaAtivoServiceImplBase {
    @Override
    public void consultaListaAtivo(ConsultaListaAtivoRequest request, StreamObserver<ConsultaListaAtivoResponse> responseObserver) {
        // 200 - Lista gerada com sucesso
        // 201 - Lista vazia
        // 401 - Erro na comunicação com repositório
        // 500 - Erro técnico não identificado.

        String msgResultado;
        int codResultado;

        Sessao dataProvider = new Sessao();
        String uniqueKey = String.valueOf(dataProvider.getUniqueKey());
        LogInterface log = dataProvider.getLoggingInterface();
        List<AtivoDtoInterface> lista = null;

        log.logInfo(uniqueKey,"Iniciando API GRPC de Consulta de ativos.");
        int tipoAtivo = request.getTipoAtivo();
        log.logInfo(uniqueKey,"Dados da Request de Lista de Ativos identificados");

        try {
            // use case
            ListarAtivosPorTipo listarAtivosPorTipo = new ListarAtivosPorTipo();
            lista = listarAtivosPorTipo.executar(dataProvider,tipoAtivo);
            if (lista.size() == 0) {
                codResultado = 201;
                msgResultado = "Lista Vazia.";
            }
            else {
                codResultado = 200;
                msgResultado = "Lista ok";
            }
        }
        catch (ComunicacaoRepositorioException e) {
            codResultado = 401;
            msgResultado = "Erro na comunicação com o Repositório!";
        }
        catch(Exception e){
            codResultado = 500;
            msgResultado = "Erro não identificado" + e.getMessage();
        }

        ConsultaListaAtivoResponse.Builder builder = ConsultaListaAtivoResponse.newBuilder()
                .setResponseCode(codResultado)
                .setResponseMessage(msgResultado);
        if (lista != null){
            builder.addAllAtivos(gerarListaAtivoObjeto(lista));
        }
        ConsultaListaAtivoResponse response = builder.build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    private List<AtivoObjeto> gerarListaAtivoObjeto(List<AtivoDtoInterface> lista){
        // preciso verificar o tamanho da lista
        List<AtivoObjeto> listaAtivoObjeto = new ArrayList<AtivoObjeto>();
        for (int i = 0; i < lista.size(); i++) {
            AtivoObjeto ativoObjeto = AtivoObjeto.newBuilder().
                    setTipoAtivo(lista.get(i).getTipoAtivoInt()).
                    setDescricaoCnpjAtivo(lista.get(i).getDescricaoCNPJAtivo()).
                    setNomeAtivo(lista.get(i).getNomeAtivo()).
                    setSiglaAtivo(lista.get(i).getSigla()).
                    build();
            listaAtivoObjeto.add(ativoObjeto);
        }
        return listaAtivoObjeto;
    }
}