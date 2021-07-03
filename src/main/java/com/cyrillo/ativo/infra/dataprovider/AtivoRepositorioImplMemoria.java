package com.cyrillo.ativo.infra.dataprovider;

import com.cyrillo.ativo.core.dataprovider.AtivoRepositorioInterface;
import com.cyrillo.ativo.core.entidade.AtivoObjeto;
import com.cyrillo.ativo.core.entidade.TipoAtivo;
import com.cyrillo.ativo.core.excecao.AtivoJaExistenteException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AtivoRepositorioImplMemoria implements AtivoRepositorioInterface {
    private List<AtivoObjeto> listaAtivoObjeto = new ArrayList<>();

    public AtivoRepositorioImplMemoria(){

    }

    @Override
    public void incluir(AtivoObjeto ativoObjeto) throws AtivoJaExistenteException, SQLException {
        this.listaAtivoObjeto.add(ativoObjeto);
    }

    @Override
    public boolean consultarPorSigla(String siglaAtivo) throws SQLException {
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
    public List<AtivoObjeto> listarTodosAtivos() {
        return this.listaAtivoObjeto;

    }

    @Override
    public List<AtivoObjeto> listarAtivosPorTipo(TipoAtivo tipoAtivo) {

        return this.listaAtivoObjeto.stream()
                .filter(a -> a.getTipoAtivo().equals(tipoAtivo.getTipoAtivo()))
                .collect(Collectors.toList());
    }
}
