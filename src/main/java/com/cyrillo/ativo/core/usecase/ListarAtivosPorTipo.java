package com.cyrillo.ativo.core.usecase;

import com.cyrillo.ativo.core.dataprovider.AtivoDtoInterface;
import com.cyrillo.ativo.core.dataprovider.AtivoRepositorioInterface;
import com.cyrillo.ativo.core.dataprovider.DataProviderInterface;
import com.cyrillo.ativo.core.dataprovider.LogInterface;
import com.cyrillo.ativo.core.usecase.excecao.AtivoParametrosInvalidosException;
import com.cyrillo.ativo.core.usecase.excecao.ComunicacaoRepositorioException;

import java.util.List;

public class ListarAtivosPorTipo {
    public ListarAtivosPorTipo() {
    }

    public List<AtivoDtoInterface> executar(DataProviderInterface data, int tipoAtivo) throws ComunicacaoRepositorioException,AtivoParametrosInvalidosException {
        AtivoRepositorioInterface repo = data.getAtivoRepositorio();
        LogInterface log = data.getLoggingInterface();
        String uniqueKey = String.valueOf(data.getUniqueKey());
        List<AtivoDtoInterface> listaAtivos = null;
        listaAtivos = repo.listarAtivosPorTipo(data, tipoAtivo);
        return listaAtivos;
    }
}


