package com.cyrillo.ativo.core.usecase;

import com.cyrillo.ativo.core.dataprovider.AtivoDtoInterface;
import com.cyrillo.ativo.core.dataprovider.AtivoRepositorioInterface;
import com.cyrillo.ativo.core.usecase.excecao.AtivoParametrosInvalidosUseCaseExcecao;
import com.cyrillo.ativo.core.usecase.excecao.ComunicacaoRepoUseCaseExcecao;
import com.cyrillo.ativo.infra.config.Sessao;
import com.cyrillo.ativo.infra.dataprovider.AtivoRepositorioImplMockFalhaRepositorio;
import com.cyrillo.ativo.infra.dataprovider.AtivoRepositorioImplMockListaComDoisItens;
import com.cyrillo.ativo.infra.dataprovider.AtivoRepositorioImplMockListaNula;
import com.cyrillo.ativo.infra.dataprovider.AtivoRepositorioImplMockListaVazia;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ListarAtivosPorTipoTest {

    @Test
    void listaAtivosVazia() {
        // Passo 1: Setup dos parâmetros de entrada e objetos dependentes
        // Instanciação de um mock que satisfaz a condição do teste (identifica que a sigla já existe)
        AtivoRepositorioInterface repositorio = new AtivoRepositorioImplMockListaVazia();
        Sessao dataProvider = new Sessao();
        dataProvider.setAtivoRepositorio(repositorio);
        int tipoAtivo = 1;
        List<AtivoDtoInterface> lista = null;
        // Passo 2: Execução do método
        try {
            lista = new ListarAtivosPorTipo().executar(dataProvider, tipoAtivo);
        }
        catch (Exception E) {
        }

        // Passo 3: Verifica se o resultado ocorrido no método é igual ao resultado esperado (
        assertEquals(lista.size(), 0);
    }

    @Test
    void listaAtivosRepoRetornaNulo() {
        // Passo 1: Setup dos parâmetros de entrada e objetos dependentes
        // Instanciação de um mock que satisfaz a condição do teste (identifica que a sigla já existe)
        AtivoRepositorioInterface repositorio = new AtivoRepositorioImplMockListaNula();
        Sessao dataProvider = new Sessao();
        dataProvider.setAtivoRepositorio(repositorio);
        int tipoAtivo = 1;
        List<AtivoDtoInterface> lista = null;
        // Passo 2: Execução do método
        try {
            lista = new ListarAtivosPorTipo().executar(dataProvider, tipoAtivo);
        }
        catch (Exception E) {
        }

        // Passo 3: Verifica se o resultado ocorrido no método é igual ao resultado esperado (
        assertEquals(lista.size(), 0);
    }

    @Test
    void erroListaAtivosTipoAtivoErrado() {
        // Passo 1: Setup dos parâmetros de entrada e objetos dependentes
        // Instanciação de um mock que satisfaz a condição do teste (identifica que a sigla já existe)
        AtivoRepositorioInterface repositorio = new AtivoRepositorioImplMockListaNula();
        Sessao dataProvider = new Sessao();
        dataProvider.setAtivoRepositorio(repositorio);
        int tipoAtivo = 5;
        List<AtivoDtoInterface> lista = null;
        // Passo 2: Execução do método
        Throwable throwable =  assertThrows(Throwable.class, () -> {
            new ListarAtivosPorTipo().executar(dataProvider, tipoAtivo);
        });
        // Passo 3: Verifica se o resultado ocorrido no método é igual ao resultado esperado (
        assertEquals(AtivoParametrosInvalidosUseCaseExcecao.class, throwable.getClass());

    }

    @Test
    void falhaListaAtivosComunicacaoRepo() {
        // Passo 1: Setup dos parâmetros de entrada e objetos dependentes
        // Instanciação de um mock que satisfaz a condição do teste (identifica que a sigla já existe)
        AtivoRepositorioInterface repositorio = new AtivoRepositorioImplMockFalhaRepositorio();
        Sessao dataProvider = new Sessao();
        dataProvider.setAtivoRepositorio(repositorio);
        int tipoAtivo = 1;
        List<AtivoDtoInterface> lista = null;
        // Passo 2: Execução do método
        Throwable throwable =  assertThrows(Throwable.class, () -> {
            new ListarAtivosPorTipo().executar(dataProvider, tipoAtivo);
        });
        // Passo 3: Verifica se o resultado ocorrido no método é igual ao resultado esperado (
        assertEquals(ComunicacaoRepoUseCaseExcecao.class, throwable.getClass());
    }


    @Test
    void sucessoListaAtivosComDoisitens() {
        // Passo 1: Setup dos parâmetros de entrada e objetos dependentes
        // Instanciação de um mock que satisfaz a condição do teste (identifica que a sigla já existe)
        AtivoRepositorioInterface repositorio = new AtivoRepositorioImplMockListaComDoisItens();
        Sessao dataProvider = new Sessao();
        dataProvider.setAtivoRepositorio(repositorio);
        int tipoAtivo = 1;
        List<AtivoDtoInterface> lista = null;
        // Passo 2: Execução do método

        try {
            lista = new ListarAtivosPorTipo().executar(dataProvider, tipoAtivo);
        }
        catch (Exception E) {
        }

        // Passo 3: Verifica se o resultado ocorrido no método é igual ao resultado esperado (
        assertEquals(lista.size(), 2);
    }
}