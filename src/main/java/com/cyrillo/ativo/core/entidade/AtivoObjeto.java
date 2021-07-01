package com.cyrillo.ativo.core.entidade;

import com.cyrillo.ativo.core.excecao.AtivoParametrosInvalidosException;

public class AtivoObjeto {
    private String sigla;
    private String nomeAtivo;
    private String descricaoCNPJAtivo;
    private TipoAtivo tipoAtivo;
    public AtivoObjeto(String sigla, String nomeAtivo, String descricaoCNPJAtivo, TipoAtivo tipoAtivo) throws AtivoParametrosInvalidosException {
        if (descricaoCNPJAtivo == null || !descricaoCNPJAtivo.matches("^\\d{2}\\.\\d{3}\\.\\d{3}\\/\\d{4}\\-\\d{2}$")){
            throw new AtivoParametrosInvalidosException("CNPJ inválido na criação de um objeto do tipo AtivoObjeto");
        }
        this.sigla = sigla;
        this.nomeAtivo = nomeAtivo;
        this.descricaoCNPJAtivo = descricaoCNPJAtivo;
        this.tipoAtivo = tipoAtivo;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AtivoObjeto other = (AtivoObjeto) obj;
        if (sigla == null) {
            if (other.sigla != null)
                return false;
        } else if (!sigla.equals(other.sigla))
            return false;
        return true;
    }


    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getNomeAtivo() {
        return nomeAtivo;
    }

    public void setNomeAtivo(String nomeAtivo) {
        this.nomeAtivo = nomeAtivo;
    }

    public String getDescricaoCNPJAtivo() {
        return descricaoCNPJAtivo;
    }

    public void setDescricaoCNPJAtivo(String descricaoCNPJAtivo) {
        if (descricaoCNPJAtivo == null || !descricaoCNPJAtivo.matches("^\\d{2}\\.\\d{3}\\.\\d{3}\\/\\d{4}\\-\\d{2}$")){
            throw new IllegalArgumentException("CNPJ inválido na criação de um objeto do tipo AtivoObjeto");
        }

        this.descricaoCNPJAtivo = descricaoCNPJAtivo;
    }

    public TipoAtivo getTipoAtivo() {
        return tipoAtivo;
    }

    public void setTipoAtivo(TipoAtivo tipoAtivo) {
        this.tipoAtivo = tipoAtivo;
    }
}
