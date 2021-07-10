package com.cyrillo.ativo.core.dataprovider;

import com.cyrillo.ativo.core.usecase.excecao.AtivoParametrosInvalidosUseCaseExcecao;
import com.cyrillo.ativo.core.dataprovider.excecao.ComunicacaoRepoDataProvExcecao;

import java.util.List;

// Acerto exceção
public interface AtivoRepositorioInterface {
    void incluir(DataProviderInterface data, AtivoDtoInterface ativoObjeto) throws ComunicacaoRepoDataProvExcecao;
    boolean consultarPorSigla(DataProviderInterface data, String siglaAtivo) throws ComunicacaoRepoDataProvExcecao;
    List<AtivoDtoInterface> listarTodosAtivos(DataProviderInterface data);
    List<AtivoDtoInterface> listarAtivosPorTipo(DataProviderInterface data, int tipoAtivo) throws ComunicacaoRepoDataProvExcecao, AtivoParametrosInvalidosUseCaseExcecao;
    void healthCheck(DataProviderInterface data) throws ComunicacaoRepoDataProvExcecao;
}
