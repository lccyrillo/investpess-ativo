package com.cyrillo.ativo.core.dataprovider.tipos;

import com.cyrillo.ativo.core.entidade.excecao.AtivoParametrosInvalidosException;
import com.cyrillo.ativo.core.entidade.excecao.FalhaComunicacaoRepositorioException;

import java.util.List;

public interface AtivoRepositorioInterface {
    void incluir(AtivoDtoInterface ativoObjeto) throws FalhaComunicacaoRepositorioException;
    boolean consultarPorSigla(String siglaAtivo) throws FalhaComunicacaoRepositorioException;
    List<AtivoDtoInterface> listarTodosAtivos();
    List<AtivoDtoInterface> listarAtivosPorTipo(int tipoAtivo) throws FalhaComunicacaoRepositorioException, AtivoParametrosInvalidosException;
}
