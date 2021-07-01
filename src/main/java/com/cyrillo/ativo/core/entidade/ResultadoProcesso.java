package com.cyrillo.ativo.core.entidade;

public class ResultadoProcesso {

    private int codigoResultado; // pode ser utilizado como quiser.
    private String mensagemResultado;
    private String stackTrace;
    private int tipoClasseResultado;
    private boolean resultadoOk;

    // tipoClasseResultado (identifica se foi possivel processar a solicitação)
    // 1 - Processado com sucesso, mas com Aviso - Warning / Erro de negócio
    // 2 - Processado com sucesso
    // 3 - Erro técnico (conexão, serviço indisponivel, timeout, ...
    // 4 - Erro técnico não identificado
    // codigoResultado (Detalhe o resultado de processamento)
    // deve ser montado a cada processamento em cada método
    // Exemplo:
    // 101 - Já existe ativo com essa sigla
    // 201 - Ativo cadastrado com sucesso
    // 301 - Falha na comunicação com banco de dados


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
