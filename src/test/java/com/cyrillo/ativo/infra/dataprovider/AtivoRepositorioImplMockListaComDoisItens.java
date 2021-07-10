package com.cyrillo.ativo.infra.dataprovider;

import com.cyrillo.ativo.core.dataprovider.AtivoDtoInterface;
import com.cyrillo.ativo.core.dataprovider.AtivoRepositorioInterface;
import com.cyrillo.ativo.core.dataprovider.DataProviderInterface;
import com.cyrillo.ativo.core.dataprovider.dto.AtivoDto;
import com.cyrillo.ativo.core.dataprovider.excecao.ComunicacaoRepoDataProvExcecao;
import com.cyrillo.ativo.core.usecase.excecao.AtivoParametrosInvalidosUseCaseExcecao;

import java.util.ArrayList;
import java.util.List;

public class AtivoRepositorioImplMockListaComDoisItens implements AtivoRepositorioInterface {
    @Override
    public void incluir(DataProviderInterface data, AtivoDtoInterface ativoObjeto) throws ComunicacaoRepoDataProvExcecao {

    }

    @Override
    public boolean consultarPorSigla(DataProviderInterface data, String siglaAtivo) throws ComunicacaoRepoDataProvExcecao {
        return false;
    }

    @Override
    public List<AtivoDtoInterface> listarTodosAtivos(DataProviderInterface data) {
        return null;
    }

    @Override
    public List<AtivoDtoInterface> listarAtivosPorTipo(DataProviderInterface data, int tipoAtivo) throws ComunicacaoRepoDataProvExcecao, AtivoParametrosInvalidosUseCaseExcecao {
        List<AtivoDtoInterface> listaAtivoObjeto = new ArrayList<>();
        AtivoDto ativoObjeto = new AtivoDto("ITUB4","ITAU UNIBANCO HOLDING S.A.","60.872.504/0001-23",1);
        listaAtivoObjeto.add(ativoObjeto);
        ativoObjeto = new AtivoDto("B3SA3","B3 S.A. - BRASIL. BOLSA. BALCÃO","09.346.601/0001-25",1);
        listaAtivoObjeto.add(ativoObjeto);
        return listaAtivoObjeto;
    }
    @Override
    public void healthCheck(DataProviderInterface data) throws ComunicacaoRepoDataProvExcecao {

    }
}
