package com.cyrillo.ativo.core.dataprovider;
import com.cyrillo.ativo.core.dataprovider.excecao.FalhaObterConexaoRepositorioExcecao;

import java.sql.Connection;

public interface ConexaoInterface {
    public Connection getConnection() throws FalhaObterConexaoRepositorioExcecao;
    public void setConnectionAtiva(boolean conexaoAtiva);
}
