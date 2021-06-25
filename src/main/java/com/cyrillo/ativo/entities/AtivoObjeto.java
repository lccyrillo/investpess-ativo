package com.cyrillo.ativo.entities;

public class AtivoObjeto {
    private String sigla;
    private String nomeAtivo;
    private String descricaoAtivo;
    private TipoAtivo tipoAtivo;
    public AtivoObjeto(String sigla, String nomeAtivo, String descricaoAtivo, TipoAtivo tipoAtivo) {
        this.sigla = sigla;
        this.nomeAtivo = nomeAtivo;
        this.descricaoAtivo = descricaoAtivo;
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

    public String getDescricaoAtivo() {
        return descricaoAtivo;
    }

    public void setDescricaoAtivo(String descricaoAtivo) {
        this.descricaoAtivo = descricaoAtivo;
    }

    public TipoAtivo getTipoAtivo() {
        return tipoAtivo;
    }

    public void setTipoAtivo(TipoAtivo tipoAtivo) {
        this.tipoAtivo = tipoAtivo;
    }
}
