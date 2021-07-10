package com.cyrillo.ativo.infra.entrypoint.servicogrpc;

import com.cyrillo.ativo.core.dataprovider.AtivoDtoInterface;
import com.cyrillo.ativo.core.dataprovider.DataProviderInterface;
import com.cyrillo.ativo.core.dataprovider.LogInterface;
import com.cyrillo.ativo.core.usecase.excecao.AtivoJaExistenteUseCaseExcecao;
import com.cyrillo.ativo.core.usecase.excecao.AtivoParametrosInvalidosUseCaseExcecao;
import com.cyrillo.ativo.core.usecase.excecao.ComunicacaoRepoUseCaseExcecao;
import com.cyrillo.ativo.infra.entrypoint.servicogrpc.ativoobjetoproto.*;
import com.cyrillo.ativo.infra.facade.FacadeAtivo;
import io.grpc.stub.StreamObserver;

import java.util.ArrayList;
import java.util.List;

public class AtivoServerService extends AtivoServerServiceGrpc.AtivoServerServiceImplBase {
    private DataProviderInterface data;

    public AtivoServerService(DataProviderInterface dataProviderInterface){
        this.data = dataProviderInterface;
    }

    @Override
    public void cadastraAtivoObjeto(CadastraAtivoObjetoRequest request, StreamObserver<CadastraAtivoObjetoResponse> responseObserver) {
        // 101 - Já existe ativo com essa sigla
        // 201 - Ativo cadastrado com sucesso
        // 401 - Falha técnica na inclusão de um ativo

        String msgResultado;
        int codResultado;

        DataProviderInterface dataProvider = data.geraSessao();
        String uniqueKey = String.valueOf(dataProvider.getUniqueKey());
        LogInterface log = dataProvider.getLoggingInterface();


        log.logInfo(uniqueKey,"Iniciando cadastra ativo objeto.");
        com.cyrillo.ativo.infra.entrypoint.servicogrpc.ativoobjetoproto.AtivoObjeto ativo = request.getAtivo();
        String sigla_ativo = ativo.getSiglaAtivo();
        String nome_ativo = ativo.getNomeAtivo();
        String descricao_cnpj_ativo = ativo.getDescricaoCnpjAtivo();
        int tipo_ativo = ativo.getTipoAtivo();

        log.logInfo(uniqueKey,"Dados do ativo identificados.");

        try {
            new FacadeAtivo().executarIncluirNovoAtivo(dataProvider,sigla_ativo,nome_ativo,descricao_cnpj_ativo,tipo_ativo);
            codResultado = 200;
            msgResultado = "Ativo: " + nome_ativo + " cadastrado!";
        }
        catch (ComunicacaoRepoUseCaseExcecao e) {
            codResultado = 401;
            msgResultado = "Erro na persistência do Ativo no banco de dados!";
        }
        catch (AtivoJaExistenteUseCaseExcecao e) {
            codResultado = 101;
            msgResultado = e.getMessage();
        }
        catch (AtivoParametrosInvalidosUseCaseExcecao e) {
            codResultado = 102;
            msgResultado = e.getMessage();
        }
        catch(Exception e){
            codResultado = 500;
            msgResultado = "Erro não identificado. " + e.getMessage();
        }
        CadastraAtivoObjetoResponse response = CadastraAtivoObjetoResponse.newBuilder()
                .setResponseCode(codResultado)
                .setResponseMessage(msgResultado)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void consultaListaAtivo(ConsultaListaAtivoRequest request, StreamObserver<ConsultaListaAtivoResponse> responseObserver) {
        // 200 - Lista gerada com sucesso
        // 201 - Lista vazia
        // 401 - Erro na comunicação com repositório
        // 402 - Tipo Ativo inválido enviado na consulta
        // 500 - Erro técnico não identificado.

        String msgResultado;
        int codResultado;

        DataProviderInterface dataProvider = data.geraSessao();
        String uniqueKey = String.valueOf(dataProvider.getUniqueKey());
        LogInterface log = dataProvider.getLoggingInterface();
        List<AtivoDtoInterface> lista = null;

        log.logInfo(uniqueKey,"Iniciando API GRPC de Consulta de ativos.");
        int tipoAtivo = request.getTipoAtivo();
        log.logInfo(uniqueKey,"Dados da Request de Lista de Ativos identificados");

        try {
            // use case
            lista = new FacadeAtivo().executarListarAtivosPorTipo(dataProvider,tipoAtivo);
            if (lista.size() == 0) {
                codResultado = 201;
                msgResultado = "Lista Vazia.";
            }
            else {
                codResultado = 200;
                msgResultado = "Lista ok";
            }
        }
        catch (ComunicacaoRepoUseCaseExcecao e) {
            codResultado = 401;
            msgResultado = "Erro na comunicação com o Repositório!";
        }
        catch (AtivoParametrosInvalidosUseCaseExcecao e) {
            codResultado = 402;
            msgResultado = "Tipo Ativo inválido enviado na consulta";
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
        for (AtivoDtoInterface ativoDtoInterface : lista) {
            AtivoObjeto ativoObjeto = AtivoObjeto.newBuilder().
                    setTipoAtivo(ativoDtoInterface.getTipoAtivoInt()).
                    setDescricaoCnpjAtivo(ativoDtoInterface.getDescricaoCNPJAtivo()).
                    setNomeAtivo(ativoDtoInterface.getNomeAtivo()).
                    setSiglaAtivo(ativoDtoInterface.getSigla()).
                    build();
            listaAtivoObjeto.add(ativoObjeto);
        }

        return listaAtivoObjeto;
    }

}