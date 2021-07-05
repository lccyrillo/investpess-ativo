package com.cyrillo.ativo.core.dataprovider.tipos;

import com.cyrillo.ativo.core.entidade.excecao.AtivoParametrosInvalidosException;
import com.cyrillo.ativo.core.entidade.excecao.FalhaComunicacaoRepositorioException;

import java.util.List;

public interface AtivoRepositorioInterface {
    void incluir(DataProviderInterface data, AtivoDtoInterface ativoObjeto) throws FalhaComunicacaoRepositorioException;
    boolean consultarPorSigla(DataProviderInterface data, String siglaAtivo) throws FalhaComunicacaoRepositorioException;
    List<AtivoDtoInterface> listarTodosAtivos(DataProviderInterface data);
    List<AtivoDtoInterface> listarAtivosPorTipo(DataProviderInterface data, int tipoAtivo) throws FalhaComunicacaoRepositorioException, AtivoParametrosInvalidosException;
}
