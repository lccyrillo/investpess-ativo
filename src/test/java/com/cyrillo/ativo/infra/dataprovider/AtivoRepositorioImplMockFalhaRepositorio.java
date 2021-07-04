package com.cyrillo.ativo.infra.dataprovider;

import com.cyrillo.ativo.core.dataprovider.AtivoRepositorioInterface;
import com.cyrillo.ativo.core.entidade.AtivoObjeto;
import com.cyrillo.ativo.core.entidade.TipoAtivo;
import com.cyrillo.ativo.core.entidade.excecao.FalhaComunicacaoRepositorioException;

import java.util.List;

public class AtivoRepositorioImplMockFalhaRepositorio implements AtivoRepositorioInterface {
    public AtivoRepositorioImplMockFalhaRepositorio(){}

    @Override
    public void incluir(AtivoObjeto ativoObjeto) throws FalhaComunicacaoRepositorioException {
        throw new FalhaComunicacaoRepositorioException("Falha comunicação Repositório");
    }
    @Override
    public boolean consultarPorSigla(String siglaAtivo) throws FalhaComunicacaoRepositorioException {
        throw new FalhaComunicacaoRepositorioException("Falha comunicação Repositório");
    }

    @Override
    public List<AtivoObjeto> listarTodosAtivos() {
        return null;
    }

    @Override
    public List<AtivoObjeto> listarAtivosPorTipo(TipoAtivo tipoAtivo) {
        return null;
    }
}