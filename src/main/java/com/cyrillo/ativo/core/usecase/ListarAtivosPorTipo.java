package com.cyrillo.ativo.core.usecase;

import com.cyrillo.ativo.core.dataprovider.tipos.AtivoDtoInterface;
import com.cyrillo.ativo.core.dataprovider.tipos.AtivoRepositorioInterface;
import com.cyrillo.ativo.core.dataprovider.tipos.DataProviderInterface;
import com.cyrillo.ativo.core.dataprovider.tipos.LoggingInterface;
import com.cyrillo.ativo.core.entidade.excecao.AtivoParametrosInvalidosException;
import com.cyrillo.ativo.core.entidade.excecao.FalhaComunicacaoRepositorioException;

import java.util.List;

public class ListarAtivosPorTipo {
    public ListarAtivosPorTipo() {
    }

    public List<AtivoDtoInterface> executar(DataProviderInterface data, int tipoAtivo) throws FalhaComunicacaoRepositorioException {
        AtivoRepositorioInterface repo = data.getAtivoRepositorio();
        LoggingInterface log = data.getLoggingInterface();
        String uniqueKey = String.valueOf(data.getUniqueKey());
        List<AtivoDtoInterface> listaAtivos = null;

        try {
            listaAtivos = repo.listarAtivosPorTipo(tipoAtivo);
        }
        catch (FalhaComunicacaoRepositorioException e) {
            e.printStackTrace();
        }
        catch (AtivoParametrosInvalidosException e) {
            e.printStackTrace();
        }
        return listaAtivos;
    }
}


