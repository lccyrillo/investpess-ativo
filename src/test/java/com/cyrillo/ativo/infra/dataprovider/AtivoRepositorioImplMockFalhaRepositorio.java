package com.cyrillo.ativo.infra.dataprovider;

import com.cyrillo.ativo.core.dataprovider.AtivoDtoInterface;
import com.cyrillo.ativo.core.dataprovider.AtivoRepositorioInterface;
import com.cyrillo.ativo.core.dataprovider.DataProviderInterface;
import com.cyrillo.ativo.core.usecase.excecao.ComunicacaoRepositorioException;

import java.util.List;

public class AtivoRepositorioImplMockFalhaRepositorio implements AtivoRepositorioInterface {
    public AtivoRepositorioImplMockFalhaRepositorio(){}

    @Override
    public void incluir(DataProviderInterface data,AtivoDtoInterface ativoObjeto) throws ComunicacaoRepositorioException {
        throw new ComunicacaoRepositorioException("Falha comunicação Repositório");
    }
    @Override
    public boolean consultarPorSigla(DataProviderInterface data,String siglaAtivo) throws ComunicacaoRepositorioException {
        throw new ComunicacaoRepositorioException("Falha comunicação Repositório");
    }

    @Override
    public List<AtivoDtoInterface> listarTodosAtivos(DataProviderInterface data) {
        return null;
    }

    @Override
    public List<AtivoDtoInterface> listarAtivosPorTipo(DataProviderInterface data,int tipoAtivo) {
        return null;
    }
}