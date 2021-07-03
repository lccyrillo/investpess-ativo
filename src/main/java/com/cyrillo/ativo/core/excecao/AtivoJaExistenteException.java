package com.cyrillo.ativo.core.excecao;

public class AtivoJaExistenteException extends Exception {
    public AtivoJaExistenteException(String siglaAtivo){
        super("Ativo com a Sigla: " + siglaAtivo+ " já existe no repositório");
    }
}