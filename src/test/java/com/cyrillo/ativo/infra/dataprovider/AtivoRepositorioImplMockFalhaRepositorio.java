package com.cyrillo.ativo.infra.dataprovider;

import com.cyrillo.ativo.core.dataprovider.tipos.AtivoDtoInterface;
import com.cyrillo.ativo.core.dataprovider.tipos.AtivoRepositorioInterface;
import com.cyrillo.ativo.core.dataprovider.tipos.DataProviderInterface;
import com.cyrillo.ativo.core.entidade.excecao.FalhaComunicacaoRepositorioException;

import java.util.List;

public class AtivoRepositorioImplMockFalhaRepositorio implements AtivoRepositorioInterface {
    public AtivoRepositorioImplMockFalhaRepositorio(){}

    @Override
    public void incluir(DataProviderInterface data,AtivoDtoInterface ativoObjeto) throws FalhaComunicacaoRepositorioException {
        throw new FalhaComunicacaoRepositorioException("Falha comunicação Repositório");
    }
    @Override
    public boolean consultarPorSigla(DataProviderInterface data,String siglaAtivo) throws FalhaComunicacaoRepositorioException {
        throw new FalhaComunicacaoRepositorioException("Falha comunicação Repositório");
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