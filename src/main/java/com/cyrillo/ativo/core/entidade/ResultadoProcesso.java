package com.cyrillo.ativo.core.entidade;

public class ResultadoProcesso {

    private int codigoResultado; // pode ser utilizado como quiser.
    private String mensagemResultado;
    private String stackTrace;
    private int tipoClasseResultado;
    private boolean resultadoOk;

    // tipoClasseResultado
    // 1 - Resultado esperado ok
    // 2 - Resultado esperado ok, mas com Aviso - Warning
    // 3 - Erro funcional
    // 4 - Erro técnico (conexão, serviço indisponivel, timeout, ....

    public ResultadoProcesso(int codigoResultado, String mensagemResultado, String stackTrace, int tipoClasseResultado) {
        this.codigoResultado = codigoResultado;
        this.mensagemResultado = mensagemResultado;
        this.stackTrace = stackTrace;
        this.tipoClasseResultado = tipoClasseResultado;
        if (this.tipoClasseResultado >=1 && this.tipoClasseResultado <=2) {
            this.resultadoOk = true;
        }
        else this.resultadoOk = false;
    }

    public int getCodigoResultado() {
        return codigoResultado;
    }


    public String getMensagemResultado() {
        return mensagemResultado;
    }

    public String getStackTrace() {
        return stackTrace;
    }


    public int getTipoClasseResultado() {
        return tipoClasseResultado;
    }

    public boolean isResultadoOk() {
        return resultadoOk;
    }


}
