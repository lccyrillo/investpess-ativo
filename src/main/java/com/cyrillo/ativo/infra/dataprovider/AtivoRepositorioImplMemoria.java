package com.cyrillo.ativo.infra.dataprovider;

import com.cyrillo.ativo.core.dataprovider.AtivoDtoInterface;
import com.cyrillo.ativo.core.dataprovider.AtivoRepositorioInterface;
import com.cyrillo.ativo.core.dataprovider.DataProviderInterface;
import com.cyrillo.ativo.core.usecase.excecao.ComunicacaoRepositorioException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AtivoRepositorioImplMemoria implements AtivoRepositorioInterface {
    private List<AtivoDtoInterface> listaAtivoObjeto = new ArrayList<>();

    public AtivoRepositorioImplMemoria(){

    }

    @Override
    public void incluir(DataProviderInterface data, AtivoDtoInterface ativoObjeto) throws ComunicacaoRepositorioException {
        this.listaAtivoObjeto.add(ativoObjeto);
    }

    @Override
    public boolean consultarPorSigla(DataProviderInterface data, String siglaAtivo) throws ComunicacaoRepositorioException {
        if (this.listaAtivoObjeto.stream()
            .filter(a -> a.getSigla().equals(siglaAtivo))
            .findFirst().isPresent()) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public List<AtivoDtoInterface> listarTodosAtivos(DataProviderInterface data) {
        return this.listaAtivoObjeto;

    }

    @Override
    public List<AtivoDtoInterface> listarAtivosPorTipo(DataProviderInterface data, int tipoAtivo) {

        return this.listaAtivoObjeto.stream()
                .filter(a -> a.getTipoAtivoInt() == tipoAtivo)
                .collect(Collectors.toList());
    }
}
