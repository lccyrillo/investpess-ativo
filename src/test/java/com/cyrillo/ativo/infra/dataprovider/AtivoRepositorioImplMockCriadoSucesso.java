package com.cyrillo.ativo.infra.dataprovider;

import com.cyrillo.ativo.core.dataprovider.AtivoDtoInterface;
import com.cyrillo.ativo.core.dataprovider.AtivoRepositorioInterface;
import com.cyrillo.ativo.core.dataprovider.DataProviderInterface;
import com.cyrillo.ativo.core.dataprovider.excecao.ComunicacaoRepoDataProvExcecao;

import java.util.List;

public class AtivoRepositorioImplMockCriadoSucesso implements AtivoRepositorioInterface {
    public AtivoRepositorioImplMockCriadoSucesso(){}
    @Override
    public void incluir(DataProviderInterface data, AtivoDtoInterface ativoObjeto) throws ComunicacaoRepoDataProvExcecao {
        //throw new SQLException();
    }
    @Override
    public boolean consultarPorSigla(DataProviderInterface data, String siglaAtivo) throws ComunicacaoRepoDataProvExcecao {
        return false;
        // throws SQLException;
        // return false;
        //return true;
    }

    @Override
    public List<AtivoDtoInterface> listarTodosAtivos(DataProviderInterface data) {
        return null;
    }

    @Override
    public List<AtivoDtoInterface> listarAtivosPorTipo(DataProviderInterface data, int tipoAtivo) {
        return null;
    }

    @Override
    public void healthCheck(DataProviderInterface data) throws ComunicacaoRepoDataProvExcecao {

    }
}