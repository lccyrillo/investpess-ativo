package com.cyrillo.ativo.core.entidade;

import com.cyrillo.ativo.core.entidade.excecao.ParametroTipoInvalidoEntExcecao;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class TipoAtivoTest {
    @Test
    void naoDeveriaCriarTipoAtivoComTipoInvalido() {
        assertThrows(ParametroTipoInvalidoEntExcecao.class,() -> new TipoAtivo(5) );
        assertThrows(ParametroTipoInvalidoEntExcecao.class,() -> new TipoAtivo(4) );
        assertThrows(ParametroTipoInvalidoEntExcecao.class,() -> new TipoAtivo(0) );
        assertThrows(ParametroTipoInvalidoEntExcecao.class,() -> new TipoAtivo(-1) );
    }
}