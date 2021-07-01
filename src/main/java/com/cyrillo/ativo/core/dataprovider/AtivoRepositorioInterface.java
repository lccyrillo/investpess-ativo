package com.cyrillo.ativo.core.dataprovider;

import com.cyrillo.ativo.core.entidade.AtivoObjeto;
import com.cyrillo.ativo.core.entidade.TipoAtivo;
import com.cyrillo.ativo.core.excecao.AtivoJaExistenteException;


import java.sql.SQLException;
import java.util.List;

public interface AtivoRepositorioInterface {
    void incluir(AtivoObjeto ativoObjeto) throws AtivoJaExistenteException, SQLException;
    boolean consultarPorSigla(String siglaAtivo) throws SQLException;
    List<AtivoObjeto> listarTodosAtivos();
    List<AtivoObjeto> listarAtivosPorTipo(TipoAtivo tipoAtivo);
}
