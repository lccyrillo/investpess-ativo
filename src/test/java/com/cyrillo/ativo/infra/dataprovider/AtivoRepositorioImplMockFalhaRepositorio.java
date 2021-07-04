package com.cyrillo.ativo.infra.dataprovider;

import com.cyrillo.ativo.core.dataprovider.tipos.AtivoDtoInterface;
import com.cyrillo.ativo.core.dataprovider.tipos.AtivoRepositorioInterface;
import com.cyrillo.ativo.core.entidade.excecao.FalhaComunicacaoRepositorioException;

import java.util.List;

public class AtivoRepositorioImplMockFalhaRepositorio implements AtivoRepositorioInterface {
    public AtivoRepositorioImplMockFalhaRepositorio(){}

    @Override
    public void incluir(AtivoDtoInterface ativoObjeto) throws FalhaComunicacaoRepositorioException {
        throw new FalhaComunicacaoRepositorioException("Falha comunicação Repositório");
    }
    @Override
    public boolean consultarPorSigla(String siglaAtivo) throws FalhaComunicacaoRepositorioException {
        throw new FalhaComunicacaoRepositorioException("Falha comunicação Repositório");
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