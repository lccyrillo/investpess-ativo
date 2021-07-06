package com.cyrillo.ativo.core.usecase;

import com.cyrillo.ativo.core.dataprovider.dto.AtivoDto;
import com.cyrillo.ativo.core.dataprovider.AtivoRepositorioInterface;
import com.cyrillo.ativo.core.dataprovider.DataProviderInterface;
import com.cyrillo.ativo.core.dataprovider.LogInterface;
import com.cyrillo.ativo.core.entidade.AtivoObjeto;
import com.cyrillo.ativo.core.entidade.excecao.ParametroCNPJInvalidoException;
import com.cyrillo.ativo.core.entidade.excecao.ParametroTipoInvalidoException;
import com.cyrillo.ativo.core.usecase.excecao.AtivoJaExistenteException;
import com.cyrillo.ativo.core.usecase.excecao.AtivoParametrosInvalidosException;
import com.cyrillo.ativo.core.usecase.excecao.ComunicacaoRepositorioException;

public class IncluirNovoAtivo {

    public IncluirNovoAtivo(){}

    public void executar(DataProviderInterface data,String sigla, String nomeAtivo, String descricaoCNPJAtivo, int tipoAtivo) throws AtivoJaExistenteException, ComunicacaoRepositorioException, AtivoParametrosInvalidosException {
        // Mapa de resultados do use case
        AtivoRepositorioInterface repo = data.getAtivoRepositorio();
        LogInterface log = data.getLoggingInterface();
        String uniqueKey =String.valueOf(data.getUniqueKey());

        log.logInfo(uniqueKey,"Iniciando Use Case Incluir Novo Ativo");

        sigla = sigla.toUpperCase();
        if (repo.consultarPorSigla(data, sigla) == false) {
            // --> Se a consulta falhar na comunicação com banco de dados, vai gerar uma exceção que precisará ser tratada.
            // Posso cadastrar ativo
            AtivoDto ativoDto = new AtivoDto(sigla,nomeAtivo,descricaoCNPJAtivo,tipoAtivo);
            // Faço esse passo para garantir o Dto está criando um objeto válido.
            try {
                AtivoObjeto ativoObjeto = ativoDto.builder();
            }
            catch (ParametroCNPJInvalidoException e){
                log.logError(uniqueKey,"CNPJ Inválido");
                throw new AtivoParametrosInvalidosException("CNPJ Inválido");
            }
            catch (ParametroTipoInvalidoException e){
                log.logError(uniqueKey,"Tipo Inválido");
                throw new AtivoParametrosInvalidosException("Tipo Inválido");
            }
            repo.incluir(data, ativoDto);
            log.logInfo(uniqueKey,"Ativo incluído com sucesso");
        }
        else {
            // Erro: Sigla já existente
            // Lançar exceção AtivoJaExistenteException
            log.logInfo(uniqueKey,"Ativo já existe no repositório");
            throw new AtivoJaExistenteException(sigla);
        }
    }


}
