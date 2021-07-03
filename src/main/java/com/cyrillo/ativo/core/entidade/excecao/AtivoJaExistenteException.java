package com.cyrillo.ativo.core.entidade.excecao;

public class AtivoJaExistenteException extends Exception {
    public AtivoJaExistenteException(String siglaAtivo){
        super("Ativo com a Sigla: " + siglaAtivo+ " já existe no repositório");
    }
}