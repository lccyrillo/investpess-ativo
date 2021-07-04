package com.cyrillo.ativo.infra.dataprovider;

import com.cyrillo.ativo.core.dataprovider.tipos.AtivoDtoInterface;
import com.cyrillo.ativo.core.dataprovider.tipos.AtivoRepositorioInterface;
import com.cyrillo.ativo.core.entidade.excecao.FalhaComunicacaoRepositorioException;

import java.util.List;

public class AtivoRepositorioImplMockCriadoSucesso implements AtivoRepositorioInterface {
    public AtivoRepositorioImplMockCriadoSucesso(){}
    @Override
    public void incluir(AtivoDtoInterface ativoObjeto) throws FalhaComunicacaoRepositorioException {
        //throw new SQLException();
    }
    @Override
    public boolean consultarPorSigla(String siglaAtivo) throws FalhaComunicacaoRepositorioException {
        return false;
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