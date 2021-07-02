package com.cyrillo.ativo.core.entidade;

import com.cyrillo.ativo.core.excecao.AtivoParametrosInvalidosException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TipoAtivoTest {
    @Test
    void naoDeveriaCriarTipoAtivoComTipoInvalido() {
        assertThrows(AtivoParametrosInvalidosException.class,() -> new TipoAtivo(5) );
        assertThrows(AtivoParametrosInvalidosException.class,() -> new TipoAtivo(4) );
        assertThrows(AtivoParametrosInvalidosException.class,() -> new TipoAtivo(0) );
        assertThrows(AtivoParametrosInvalidosException.class,() -> new TipoAtivo(-1) );
    }
}