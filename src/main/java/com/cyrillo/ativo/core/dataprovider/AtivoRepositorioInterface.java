package com.cyrillo.ativo.core.dataprovider;

import com.cyrillo.ativo.core.usecase.excecao.AtivoParametrosInvalidosException;
import com.cyrillo.ativo.core.usecase.excecao.ComunicacaoRepositorioException;

import java.util.List;

public interface AtivoRepositorioInterface {
    void incluir(DataProviderInterface data, AtivoDtoInterface ativoObjeto) throws ComunicacaoRepositorioException;
    boolean consultarPorSigla(DataProviderInterface data, String siglaAtivo) throws ComunicacaoRepositorioException;
    List<AtivoDtoInterface> listarTodosAtivos(DataProviderInterface data);
    List<AtivoDtoInterface> listarAtivosPorTipo(DataProviderInterface data, int tipoAtivo) throws ComunicacaoRepositorioException, AtivoParametrosInvalidosException;
}
