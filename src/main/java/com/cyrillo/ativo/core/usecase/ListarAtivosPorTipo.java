package com.cyrillo.ativo.core.usecase;

import com.cyrillo.ativo.core.dataprovider.*;
import com.cyrillo.ativo.core.entidade.TipoAtivo;
import com.cyrillo.ativo.core.entidade.excecao.ParametroTipoInvalidoEntExcecao;
import com.cyrillo.ativo.core.usecase.excecao.AtivoParametrosInvalidosUseCaseExcecao;
import com.cyrillo.ativo.core.dataprovider.excecao.ComunicacaoRepoDataProvExcecao;
import com.cyrillo.ativo.core.usecase.excecao.ComunicacaoRepoUseCaseExcecao;

import java.util.ArrayList;
import java.util.List;

public class ListarAtivosPorTipo {
    public ListarAtivosPorTipo() {
    }

    public List<AtivoDtoInterface> executar(DataProviderInterface data, int tipoAtivo) throws ComunicacaoRepoUseCaseExcecao, AtivoParametrosInvalidosUseCaseExcecao {
        AtivoRepositorioInterface repo = data.getAtivoRepositorio();
        LogInterface log = data.getLoggingInterface();
        String uniqueKey = String.valueOf(data.getUniqueKey());
        try {
            TipoAtivo umTipoAtivo = new TipoAtivo(tipoAtivo);
            List<AtivoDtoInterface> listaAtivos = new ArrayList<AtivoDtoInterface>();
            listaAtivos = repo.listarAtivosPorTipo(data, tipoAtivo);
            if (listaAtivos == null) {
                listaAtivos = new ArrayList<AtivoDtoInterface>();
            }
            return listaAtivos;
        } catch (ParametroTipoInvalidoEntExcecao e) {
            AtivoParametrosInvalidosUseCaseExcecao falha = new AtivoParametrosInvalidosUseCaseExcecao("Tipo de Ativo inválido!");
            falha.addSuppressed(e);
            log.logError(uniqueKey,"Tipo de Ativo Inválido");
            e.printStackTrace();
            throw falha;
        }
        catch (ComunicacaoRepoDataProvExcecao e) {
            ComunicacaoRepoUseCaseExcecao falha = new ComunicacaoRepoUseCaseExcecao("Falha na comunicação do Use Case com Repositório: AtivoRepositorio");
            falha.addSuppressed(e);
            log.logError(uniqueKey, "Erro na comunicação com repositório.");
            e.printStackTrace();
            throw falha;
        }
    }
}


