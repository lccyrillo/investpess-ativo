package com.cyrillo.ativo.core.dataprovider;

import com.cyrillo.ativo.core.entidade.AtivoObjeto;
import com.cyrillo.ativo.core.entidade.TipoAtivo;
import com.cyrillo.ativo.core.entidade.ResultadoProcesso;

import java.util.List;

public interface AtivoRepositorioInterface {
    ResultadoProcesso incluir(AtivoObjeto ativoObjeto);
    AtivoObjeto consultarPorSigla(String siglaAtivo);
    List<AtivoObjeto> listarTodosAtivos();
    List<AtivoObjeto> listarAtivosPorTipo(TipoAtivo tipoAtivo);
}
