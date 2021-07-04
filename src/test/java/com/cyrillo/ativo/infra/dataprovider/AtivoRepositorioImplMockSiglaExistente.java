package com.cyrillo.ativo.infra.dataprovider;

import com.cyrillo.ativo.core.dataprovider.tipos.AtivoDtoInterface;
import com.cyrillo.ativo.core.dataprovider.tipos.AtivoRepositorioInterface;
import com.cyrillo.ativo.core.entidade.excecao.FalhaComunicacaoRepositorioException;

import java.util.List;

public class AtivoRepositorioImplMockSiglaExistente implements AtivoRepositorioInterface {
    public AtivoRepositorioImplMockSiglaExistente(){}
    @Override
    public void incluir(AtivoDtoInterface ativoObjeto) throws FalhaComunicacaoRepositorioException {
    }
    @Override
    public boolean consultarPorSigla(String siglaAtivo) throws FalhaComunicacaoRepositorioException {
        return true;
        // throws SQLException;
        // return false;
        //return true;
    }

    @Override
    public List<AtivoDtoInterface> listarTodosAtivos() {
        return null;
    }

    @Override
    public List<AtivoDtoInterface> listarAtivosPorTipo(int tipoAtivo) {
        return null;
    }
}