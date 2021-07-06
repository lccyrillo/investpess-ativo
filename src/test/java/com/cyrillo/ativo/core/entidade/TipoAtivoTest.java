package com.cyrillo.ativo.core.entidade;

import com.cyrillo.ativo.core.entidade.excecao.ParametroTipoInvalidoException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class TipoAtivoTest {
    @Test
    void naoDeveriaCriarTipoAtivoComTipoInvalido() {
        assertThrows(ParametroTipoInvalidoException.class,() -> new TipoAtivo(5) );
        assertThrows(ParametroTipoInvalidoException.class,() -> new TipoAtivo(4) );
        assertThrows(ParametroTipoInvalidoException.class,() -> new TipoAtivo(0) );
        assertThrows(ParametroTipoInvalidoException.class,() -> new TipoAtivo(-1) );
    }
}