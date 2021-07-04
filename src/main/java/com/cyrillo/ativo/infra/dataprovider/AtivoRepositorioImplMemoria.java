package com.cyrillo.ativo.infra.dataprovider;

import com.cyrillo.ativo.core.dataprovider.tipos.AtivoDtoInterface;
import com.cyrillo.ativo.core.dataprovider.tipos.AtivoRepositorioInterface;
import com.cyrillo.ativo.core.entidade.excecao.FalhaComunicacaoRepositorioException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AtivoRepositorioImplMemoria implements AtivoRepositorioInterface {
    private List<AtivoDtoInterface> listaAtivoObjeto = new ArrayList<>();

    public AtivoRepositorioImplMemoria(){

    }

    @Override
    public void incluir(AtivoDtoInterface ativoObjeto) throws FalhaComunicacaoRepositorioException {
        this.listaAtivoObjeto.add(ativoObjeto);
    }

    @Override
    public boolean consultarPorSigla(String siglaAtivo) throws FalhaComunicacaoRepositorioException {
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
    public List<AtivoDtoInterface> listarTodosAtivos() {
        return this.listaAtivoObjeto;

    }

    @Override
    public List<AtivoDtoInterface> listarAtivosPorTipo(int tipoAtivo) {

        return this.listaAtivoObjeto.stream()
                .filter(a -> a.getTipoAtivoInt() == tipoAtivo)
                .collect(Collectors.toList());
    }
}
