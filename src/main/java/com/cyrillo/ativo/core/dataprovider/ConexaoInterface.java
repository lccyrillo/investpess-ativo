package com.cyrillo.ativo.core.dataprovider;
import com.cyrillo.ativo.core.dataprovider.excecao.FalhaObterConexaoRepositorioExcecao;

import java.sql.Connection;
// Interface para apoiar o data provider
public interface ConexaoInterface {
    public Connection getConnection() throws FalhaObterConexaoRepositorioExcecao;
    public void setConnectionAtiva(boolean conexaoAtiva);
}
