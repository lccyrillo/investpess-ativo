package com.cyrillo.ativo.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TipoAtivoTest {
    @Test
    void naoDeveriaCriarTipoAtivoComTipoInvalido() {
        assertThrows(IllegalArgumentException.class,() -> new TipoAtivo(5) );
        assertThrows(IllegalArgumentException.class,() -> new TipoAtivo(4) );
        assertThrows(IllegalArgumentException.class,() -> new TipoAtivo(0) );
        assertThrows(IllegalArgumentException.class,() -> new TipoAtivo(-1) );
    }
}