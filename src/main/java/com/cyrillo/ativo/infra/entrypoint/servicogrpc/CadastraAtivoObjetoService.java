package com.cyrillo.ativo.infra.entrypoint.servicogrpc;

import com.cyrillo.ativo.core.dataprovider.LoggingInterface;
import com.cyrillo.ativo.core.excecao.AtivoJaExistenteException;
import com.cyrillo.ativo.core.usecase.IncluirNovoAtivo;
import com.cyrillo.ativo.infra.config.Sessao;
import io.grpc.stub.StreamObserver;
import proto.ativo.ativoobjetoproto.CadastraAtivoObjetoRequest;
import proto.ativo.ativoobjetoproto.CadastraAtivoObjetoResponse;
import proto.ativo.ativoobjetoproto.CadastraAtivoObjetoServiceGrpc;

import java.sql.SQLException;
import java.util.UUID;

public class CadastraAtivoObjetoService  extends CadastraAtivoObjetoServiceGrpc.CadastraAtivoObjetoServiceImplBase {
    @Override
    public void cadastraAtivoObjeto(CadastraAtivoObjetoRequest request, StreamObserver<CadastraAtivoObjetoResponse> responseObserver) {
        // 101 - Já existe ativo com essa sigla
        // 201 - Ativo cadastrado com sucesso
        // 401 - Falha técnica na inclusão de um ativo

        String msgResultado;
        String codResultado;

        Sessao dataProvider = new Sessao();
        UUID uniqueKey = dataProvider.getUniqueKey();
        LoggingInterface log = dataProvider.getLoggingInterface();


        log.logInfo(String.valueOf(uniqueKey),"iniciando cadastra ativo objeto");
        proto.ativo.ativoobjetoproto.AtivoObjeto ativo = request.getAtivo();
        String sigla_ativo = ativo.getSiglaAtivo();
        String nome_ativo = ativo.getNomeAtivo();
        String descricao_cnpj_ativo = ativo.getDescricaoCnpjAtivo();
        int tipo_ativo = ativo.getTipoAtivo();

        log.logInfo(String.valueOf(uniqueKey),"Dados do ativo identifocados");

        try {
            IncluirNovoAtivo incluirNovoAtivo = new IncluirNovoAtivo();
            incluirNovoAtivo.executar(dataProvider,sigla_ativo,nome_ativo,descricao_cnpj_ativo,tipo_ativo);
            codResultado = "200";
            msgResultado = "Ativo: " + nome_ativo + " cadastrado!";
        }
        catch (SQLException e) {
            msgResultado = "401";
            codResultado = "Erro na persistência do Ativo no banco de dados!";
        }
        catch (AtivoJaExistenteException e) {
            msgResultado = e.getMessage();
            codResultado = "101";
        }
        catch(Exception e){
            msgResultado = "Erro não identificado" + e.getMessage();
            codResultado = "500";
        }
        CadastraAtivoObjetoResponse response = CadastraAtivoObjetoResponse.newBuilder()
                .setResponseCode(codResultado)
                .setResponseMessage(msgResultado)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}