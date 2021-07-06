package com.cyrillo.ativo.infra.entrypoint.servicogrpc;

import com.cyrillo.ativo.core.dataprovider.LogInterface;
import com.cyrillo.ativo.core.usecase.excecao.AtivoJaExistenteException;
import com.cyrillo.ativo.core.usecase.excecao.AtivoParametrosInvalidosException;
import com.cyrillo.ativo.core.usecase.excecao.ComunicacaoRepositorioException;
import com.cyrillo.ativo.core.usecase.IncluirNovoAtivo;
import com.cyrillo.ativo.infra.config.Sessao;
import com.cyrillo.ativo.infra.entrypoint.servicogrpc.ativoobjetoproto.CadastraAtivoObjetoRequest;
import com.cyrillo.ativo.infra.entrypoint.servicogrpc.ativoobjetoproto.CadastraAtivoObjetoResponse;
import com.cyrillo.ativo.infra.entrypoint.servicogrpc.ativoobjetoproto.CadastraAtivoObjetoServiceGrpc;
import io.grpc.stub.StreamObserver;

public class CadastraAtivoObjetoService  extends CadastraAtivoObjetoServiceGrpc.CadastraAtivoObjetoServiceImplBase {
    @Override
    public void cadastraAtivoObjeto(CadastraAtivoObjetoRequest request, StreamObserver<CadastraAtivoObjetoResponse> responseObserver) {
        // 101 - Já existe ativo com essa sigla
        // 201 - Ativo cadastrado com sucesso
        // 401 - Falha técnica na inclusão de um ativo

        String msgResultado;
        int codResultado;

        Sessao dataProvider = new Sessao();
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
            IncluirNovoAtivo incluirNovoAtivo = new IncluirNovoAtivo();
            incluirNovoAtivo.executar(dataProvider,sigla_ativo,nome_ativo,descricao_cnpj_ativo,tipo_ativo);
            codResultado = 200;
            msgResultado = "Ativo: " + nome_ativo + " cadastrado!";
        }
        catch (ComunicacaoRepositorioException e) {
            codResultado = 401;
            msgResultado = "Erro na persistência do Ativo no banco de dados!";
        }
        catch (AtivoJaExistenteException e) {
            codResultado = 101;
            msgResultado = e.getMessage();
        }
        catch (AtivoParametrosInvalidosException e) {
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

}