package com.cyrillo.ativo.core.dataprovider;
import com.cyrillo.ativo.core.dataprovider.excecao.FalhaObterConexaoRepoDataProvExcecao;

import java.sql.Connection;
// Interface para apoiar o data provider
public interface ConexaoInterface {
    public Connection getConnection() throws FalhaObterConexaoRepoDataProvExcecao;
    public void setConnectionAtiva(boolean conexaoAtiva);
}
