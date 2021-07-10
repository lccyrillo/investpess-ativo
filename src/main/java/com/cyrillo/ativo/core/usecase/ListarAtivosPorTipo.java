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
            log.logError(uniqueKey,"Tipo de Ativo Inválido");
            AtivoParametrosInvalidosUseCaseExcecao falha = new AtivoParametrosInvalidosUseCaseExcecao("Tipo de Ativo inválido!");
            falha.addSuppressed(e);
            throw falha;
        }
        catch (ComunicacaoRepoDataProvExcecao comunicacaoRepoDataProvExcecao) {
            ComunicacaoRepoUseCaseExcecao falha = new ComunicacaoRepoUseCaseExcecao("Falha na comunicação do Use Case com Repositório: AtivoRepositorio");
            falha.addSuppressed(comunicacaoRepoDataProvExcecao);
            log.logError(uniqueKey, "Erro na comunicação com repositório.");
            throw falha;
        }
    }
}


