package com.cyrillo.ativo.core.usecase;

import com.cyrillo.ativo.core.dataprovider.AtivoRepositorioInterface;
import com.cyrillo.ativo.core.entidade.AtivoObjeto;
import com.cyrillo.ativo.core.entidade.AtivoObjetoBuilder;
import com.cyrillo.ativo.core.excecao.AtivoJaExistenteException;
import com.cyrillo.ativo.core.excecao.AtivoParametrosInvalidosException;

import java.sql.SQLException;

public class IncluirNovoAtivo {

    public IncluirNovoAtivo(){}

    public void executar(AtivoRepositorioInterface ativoRepositorioInterface, String sigla, String nomeAtivo, String descricaoCNPJAtivo, int tipoAtivo) throws AtivoJaExistenteException, SQLException, AtivoParametrosInvalidosException {
        // Mapa de resultados do use case


        int codigoResultado;
        String mensagemResultado;
        String stackTrace = null;
        int tipoClasseResultado;


        // Procuro para ver se a sigla informada já existe


        if (ativoRepositorioInterface.consultarPorSigla(sigla) == null) {
            // --> Se a consulta falhar na comunicação com banco de dados, vai gerar uma exceção que precisará ser tratada.
            // Posso cadastrar ativo
            AtivoObjetoBuilder builderAtivo = new AtivoObjetoBuilder();
            builderAtivo.infoCompleta(sigla,nomeAtivo,descricaoCNPJAtivo,tipoAtivo);
            AtivoObjeto ativoObjeto = builderAtivo.build();
            ativoRepositorioInterface.incluir(ativoObjeto);

        }
        else {
            // Erro: Sigla já existente
            // Lançar exceção AtivoJaExistenteException
            throw new AtivoJaExistenteException("Ativo " + sigla + " já existente no repositório!");
        }


    }


}
