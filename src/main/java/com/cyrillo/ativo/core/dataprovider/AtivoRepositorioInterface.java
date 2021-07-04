package com.cyrillo.ativo.core.dataprovider;

import com.cyrillo.ativo.core.entidade.AtivoObjeto;
import com.cyrillo.ativo.core.entidade.TipoAtivo;
import com.cyrillo.ativo.core.entidade.excecao.FalhaComunicacaoRepositorioException;

import java.util.List;

public interface AtivoRepositorioInterface {
    void incluir(AtivoObjeto ativoObjeto) throws FalhaComunicacaoRepositorioException;
    boolean consultarPorSigla(String siglaAtivo) throws FalhaComunicacaoRepositorioException;
    List<AtivoObjeto> listarTodosAtivos();
    List<AtivoObjeto> listarAtivosPorTipo(TipoAtivo tipoAtivo);
}
