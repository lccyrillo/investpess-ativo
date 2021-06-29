package com.cyrillo.ativo.core.entidade;

public class ResultadoProcesso {

    private int codigoResultado; // pode ser utilizado como quiser.
    private String mensagemResultado;
    private String stackTrace;
    private int tipoClasseResultado;
    private boolean resultadoOk;

    // tipoClasseResultado
    // 1 - Resultado esperado ok, mas com Aviso - Warning
    // 2 - Resultado esperado ok
    // 3 - Erro funcional
    // 4 - Erro técnico (conexão, serviço indisponivel, timeout, ...
    // 5 - Erro técnico não identificado
    //    Código	Nome	Descrição
    //    200	OK	O recurso solicitado foi processado e retornado com sucesso.
    //    201	Created	O recurso informado foi criado com sucesso.
    //    401	Unauthorized	A chave da API está desativada, incorreta ou não foi informada corretamente. Consulte a seção sobre autenticação da documentação
    //    402	Payment Required	A chave da API está correta, porém a conta foi bloqueada por inadimplência. Neste caso, acesse o painel para verificar as pendências.
    //    403	Forbidden	O acesso ao recurso não foi autorizado. Este erro por ocorrer por dois motivos: (1) Uma conexão sem criptografia foi iniciada. Neste caso utilize sempre HTTPS. (2) As configurações de perfil de acesso não permitem a ação desejada. Consulte as configurações de acesso no painel de administração.
    //    404	Not Found	O recurso solicitado ou o endpoint não foi encontrado.
    //    406	Not Acceptable	O formato enviado não é aceito. O cabeçalho Content-Type da requisição deve contar obrigatoriamente o valor application/json para requisições do tipo POST e PUT.
    //    422	Unprocessable Entity	A requisição foi recebida com sucesso, porém contém parâmetros inválidos. Para mais detalhes, verifique o atributo errors no corpo da resposta.
    //    429	Too Many Requests	O limite de requisições foi atingido. Verifique o cabeçalho Retry-After para obter o tempo de espera (em segundos) necessário para a retentativa.
    //    400	Bad Request	Não foi possível interpretar a requisição. Verifique a sintaxe das informações enviadas.
    //    500	Internal Server Error	Ocorreu uma falha na plataforma.


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
