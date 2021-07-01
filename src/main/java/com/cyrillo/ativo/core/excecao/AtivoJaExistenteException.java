package com.cyrillo.ativo.core.excecao;

public class AtivoJaExistenteException extends Exception {
    public AtivoJaExistenteException(String msg){
        super(msg);
    }
}