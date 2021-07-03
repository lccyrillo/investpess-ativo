package com.cyrillo.ativo.core.usecase;

import com.cyrillo.ativo.core.dataprovider.AtivoRepositorioInterface;
import com.cyrillo.ativo.core.dataprovider.LoggingInterface;
import com.cyrillo.ativo.core.excecao.AtivoJaExistenteException;
import com.cyrillo.ativo.infra.config.Aplicacao;
import com.cyrillo.ativo.infra.dataprovider.AtivoRepositorioImplMockCriadoSucesso;
import com.cyrillo.ativo.infra.dataprovider.AtivoRepositorioImplMockSiglaExistente;
import com.cyrillo.ativo.infra.dataprovider.LoggingInterfaceImplConsole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IncluirNovoAtivoTest {
    @Test
    @DisplayName("naoDeveriaCriarAtivoComSiglaJaExistente()")
    void naoDeveriaCriarAtivoComSiglaJaExistente() {
        // Passo 1: Setup dos parâmetros de entrada e objetos dependentes
        // Instanciação de um mock que satisfaz a condição do teste (identifica que a sigla já existe)
        AtivoRepositorioInterface repositorio = new AtivoRepositorioImplMockSiglaExistente();
        LoggingInterface log = new LoggingInterfaceImplConsole();
        Aplicacao dataProvider = Aplicacao.getInstance();
        dataProvider.setAtivoRepositorio(repositorio);
        dataProvider.setLoggingInterface(log);
        String sigla = "VALE3";
        String nomeAtivo = "VALE S.A";
        String descricaoCNPJ = "33.592.510/0001-54";
        Exception exception = null;
        int tipoAtivo = 1;
        // Passo 2: Execução do método
        IncluirNovoAtivo incluirNovoAtivo = new IncluirNovoAtivo();
        Throwable throwable =  assertThrows(Throwable.class, () -> {
            incluirNovoAtivo.executar(dataProvider, sigla, nomeAtivo, descricaoCNPJ, tipoAtivo);
        });
        // Passo 3: Verifica se o resultado ocorrido no método é igual ao resultado esperado (
        assertEquals(AtivoJaExistenteException.class, throwable.getClass());
        // Obs: exceções lançadas pelo método incluirNovoAtivo.executar //  throws AtivoJaExistenteException, SQLException, AtivoParametrosInvalidosException
    }
    @Test
    void ativoCriadoComSucesso() {
        // Passo 1: Setup dos parâmetros de entrada e objetos dependentes
        // Instanciação de um mock que satisfaz a condição do teste (identifica que a sigla já existe)
        AtivoRepositorioInterface repositorio = new AtivoRepositorioImplMockCriadoSucesso();
        LoggingInterface log = new LoggingInterfaceImplConsole();
        Aplicacao dataProvider = Aplicacao.getInstance();
        dataProvider.setAtivoRepositorio(repositorio);
        dataProvider.setLoggingInterface(log);
        String sigla = "VALE3";
        String nomeAtivo = "VALE S.A";
        String descricaoCNPJ = "33.592.510/0001-54";
        Exception exception = null;
        int tipoAtivo = 1;

        // Passo 2: Execução do método
        IncluirNovoAtivo incluirNovoAtivo = new IncluirNovoAtivo();
        try {
            incluirNovoAtivo.executar(dataProvider, sigla, nomeAtivo, descricaoCNPJ, tipoAtivo);
        }
        catch (Exception e){
            exception = e;
        }

        // Passo 3: Verifica se o método foi executado com sucesso e não teve exceção
        assertEquals(null,exception);
        // Obs: exceções lançadas pelo método incluirNovoAtivo.executar //  throws AtivoJaExistenteException, SQLException, AtivoParametrosInvalidosException
    }
}



