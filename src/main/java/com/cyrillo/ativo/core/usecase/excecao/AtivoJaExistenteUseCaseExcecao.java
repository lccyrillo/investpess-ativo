package com.cyrillo.ativo.core.usecase.excecao;

public class AtivoJaExistenteUseCaseExcecao extends Exception {
    public AtivoJaExistenteUseCaseExcecao(String siglaAtivo){
        super("Ativo com a Sigla: " + siglaAtivo+ " já existe no repositório");
    }
}