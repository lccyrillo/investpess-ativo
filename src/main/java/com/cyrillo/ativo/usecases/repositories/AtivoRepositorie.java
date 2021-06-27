package com.cyrillo.ativo.usecases.repositories;

import com.cyrillo.ativo.entities.AtivoObjeto;
import com.cyrillo.ativo.entities.TipoAtivo;

import java.util.List;

public interface AtivoRepositorie {
    int incluir(AtivoObjeto ativoObjeto);
    AtivoObjeto consultarPorSigla(String siglaAtivo);
    List<AtivoObjeto> listarTodosAtivos();
    List<AtivoObjeto> listarAtivosPorSigla(TipoAtivo tipoAtivo);
}
