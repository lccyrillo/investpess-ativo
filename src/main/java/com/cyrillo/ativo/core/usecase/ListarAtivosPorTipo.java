package com.cyrillo.ativo.core.usecase;

import com.cyrillo.ativo.core.dataprovider.*;
import com.cyrillo.ativo.core.entidade.TipoAtivo;
import com.cyrillo.ativo.core.entidade.excecao.ParametroTipoInvalidoException;
import com.cyrillo.ativo.core.usecase.excecao.AtivoParametrosInvalidosException;
import com.cyrillo.ativo.core.usecase.excecao.ComunicacaoRepositorioException;

import java.util.ArrayList;
import java.util.List;

public class ListarAtivosPorTipo {
    public ListarAtivosPorTipo() {
    }

    public List<AtivoDtoInterface> executar(DataProviderInterface data, int tipoAtivo) throws ComunicacaoRepositorioException, AtivoParametrosInvalidosException {
        AtivoRepositorioInterface repo = data.getAtivoRepositorio();
        LogInterface log = data.getLoggingInterface();
        String uniqueKey = String.valueOf(data.getUniqueKey());
        try {
            TipoAtivo umTipoAtivo = new TipoAtivo(tipoAtivo);
        } catch (ParametroTipoInvalidoException e) {
            log.logError(uniqueKey,"Tipo de Ativo Inválido");
            AtivoParametrosInvalidosException falha = new AtivoParametrosInvalidosException("Tipo de Ativo inválido!");
            falha.addSuppressed(e);
            throw falha;
        }
        List<AtivoDtoInterface> listaAtivos = new ArrayList<AtivoDtoInterface>();
        listaAtivos = repo.listarAtivosPorTipo(data, tipoAtivo);
        // Garantir padronização de retorno da lista independente do Repositório.
        // Sempre devolvo lista, mesmo que sem elementos.
        if (listaAtivos == null) {
            listaAtivos = new ArrayList<AtivoDtoInterface>();
        }
        return listaAtivos;
    }
}


