package com.cyrillo.ativo.infra.dataprovider;

import com.cyrillo.ativo.core.dataprovider.AtivoRepositorioInterface;
import com.cyrillo.ativo.core.entidade.AtivoObjeto;
import com.cyrillo.ativo.core.entidade.TipoAtivo;

import java.sql.SQLException;
import java.util.List;

public class AtivoRepositorioImplMockSiglaExistente implements AtivoRepositorioInterface {
    public AtivoRepositorioImplMockSiglaExistente(){}
    @Override
    public void incluir(AtivoObjeto ativoObjeto) throws SQLException {
    }
    @Override
    public boolean consultarPorSigla(String siglaAtivo) throws SQLException {
        return true;
        // throws SQLException;
        // return false;
        //return true;
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