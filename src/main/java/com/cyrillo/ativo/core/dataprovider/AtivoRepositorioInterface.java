package com.cyrillo.ativo.core.dataprovider;

import com.cyrillo.ativo.core.entidade.AtivoObjeto;
import com.cyrillo.ativo.core.entidade.TipoAtivo;

import java.util.List;

public interface AtivoRepositorioInterface {
    int incluir(AtivoObjeto ativoObjeto);
    AtivoObjeto consultarPorSigla(String siglaAtivo);
    List<AtivoObjeto> listarTodosAtivos();
    List<AtivoObjeto> listarAtivosPorTipo(TipoAtivo tipoAtivo);
}
