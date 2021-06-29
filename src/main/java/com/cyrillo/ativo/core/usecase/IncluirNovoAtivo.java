package com.cyrillo.ativo.core.usecase;

import com.cyrillo.ativo.core.dataprovider.AtivoRepositorioInterface;
import com.cyrillo.ativo.core.entidade.AtivoObjeto;
import com.cyrillo.ativo.core.entidade.AtivoObjetoBuilder;
import com.cyrillo.ativo.core.entidade.ResultadoProcesso;

public class IncluirNovoAtivo {

    public IncluirNovoAtivo(){}

    public ResultadoProcesso executar(AtivoRepositorioInterface ativoRepositorioInterface, String sigla, String nomeAtivo, String descricaoCNPJAtivo, int tipoAtivo) {

        // Mapa de resultados do use case
        // 0 -> sucesso
        // 1 -> Erro sigla já existente para esse tipo
        // 2 -> Erro relacionado ao repositório

        int codigoResultado = 0;

        AtivoObjetoBuilder builderAtivo = new AtivoObjetoBuilder();
        builderAtivo.infoCompleta(sigla,nomeAtivo,descricaoCNPJAtivo,tipoAtivo);
        AtivoObjeto ativoObjeto = builderAtivo.build();
        ResultadoProcesso resultado = ativoRepositorioInterface.incluir(ativoObjeto);
        String msgresultado;
        // ainda falta checar se a sigla já existe no repositorio antes de incluir no reposotirio
        return resultado;

    }


}
