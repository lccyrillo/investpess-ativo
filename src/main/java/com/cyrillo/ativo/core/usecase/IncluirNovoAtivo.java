package com.cyrillo.ativo.core.usecase;

import com.cyrillo.ativo.core.dataprovider.AtivoRepositorioInterface;
import com.cyrillo.ativo.core.dataprovider.DataProviderInterface;
import com.cyrillo.ativo.core.dataprovider.LoggingInterface;
import com.cyrillo.ativo.core.entidade.AtivoObjeto;
import com.cyrillo.ativo.core.entidade.builder.AtivoObjetoBuilder;
import com.cyrillo.ativo.core.entidade.excecao.AtivoJaExistenteException;
import com.cyrillo.ativo.core.entidade.excecao.AtivoParametrosInvalidosException;
import com.cyrillo.ativo.core.entidade.excecao.FalhaComunicacaoRepositorioException;

public class IncluirNovoAtivo {

    public IncluirNovoAtivo(){}

    public void executar(DataProviderInterface dataProviderInterface,String sigla, String nomeAtivo, String descricaoCNPJAtivo, int tipoAtivo) throws AtivoJaExistenteException, FalhaComunicacaoRepositorioException, AtivoParametrosInvalidosException {
        // Mapa de resultados do use case
        AtivoRepositorioInterface ativoRepositorioInterface = dataProviderInterface.getAtivoRepositorio();
        LoggingInterface loggingInterface = dataProviderInterface.getLoggingInterface();

        sigla = sigla.toUpperCase();
        if (ativoRepositorioInterface.consultarPorSigla(sigla) == false) {
            // --> Se a consulta falhar na comunicação com banco de dados, vai gerar uma exceção que precisará ser tratada.
            // Posso cadastrar ativo
            AtivoObjetoBuilder builderAtivo = new AtivoObjetoBuilder();
            builderAtivo.infoCompleta(sigla,nomeAtivo,descricaoCNPJAtivo,tipoAtivo);
            AtivoObjeto ativoObjeto = builderAtivo.build();
            ativoRepositorioInterface.incluir(ativoObjeto);
        }
        else {
            // Erro: Sigla já existente
            // Lançar exceção AtivoJaExistenteException
            throw new AtivoJaExistenteException(sigla);
        }
    }


}
