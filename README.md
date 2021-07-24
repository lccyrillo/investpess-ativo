# Projeto: investpess-ativo
* **Data criação:** 23/06/2021
* **Criador:** Luciano Cavallini Cyrillo
* **Contexto:** Microsserviço para cadastrar e consultar Ativos da BMF / Bovespa
####

## **Sumário**
* [Objetivo](#objetivo)
* [Tecnologias Utilizadas](#tecnologias)
* [Como rodar a aplicação](#howto)
* [Contatos](#contatos)

***

## Objetivo

> #### Essa implementação tem como objetivo apresentar uma abordagem prática de uso da Clean Architecture.
> #### Nos últimos dois anos, estudei o tema através da leitura do livro do Robert C. Martin e também de artigos relacionados ao tema. De forma geral, achei o conteúdo bastante denso e com muitos conceitos importantes, mas de difícil compreensão. Dessa forma, resolvi aplicar os conceitos através da implementação do modelo em um microsserviço com linguagem Java.
> #### A implementação do caso de uso prático foi feita com base nos modelos de arquitetura Clean Architecture Simplificada detalhados no artigo do Guilherme Biff Zarelli e no github do Mattia Battiston.

## Tecnologias

* Microsserviço com java / gRPC
* banco de dados postgres
* docker file para subir banco de dados postgres

## How To
#### Executar os seguintes passos para preparar a aplicação:
1. Rodar o build.gradle para puxar as dependências.
2. Rodar a geração dos arquivos proto para gerar os stubs do gRPC
3. Setar o arquivo de configuração localizado na pasta: config\config.properties
*  se estiver configurado para: repositorio.implementacao=postgres, precisa levantar o docker de banco de dados
*  se estiver configurado para: repositorio.implementacao=memoria, não precisa do docker
4. Levantar o docker
* Entrar no diretorio: \docker\docker_db e rodar os comandos:
* Criar a imagem
```sh
docker image build -t docker_db .
```
* Levantar o container
```sh
docker container run --name docker_db_container -p 5433:5432 -v ./scripts:/scripts -v C:\Arquivos\01_PRJSW\investpess-teste\investpess-ativo\docker\docker_db\scripts\init.sql:/docker-entrypoint-initdb.d/init.sql -v dados_dev:/var/lib/postgresql/data docker_db
```
5. Verificar se o banco de dados foi criado com sucesso com base no script init.sql . 
* Abrir outro terminal e executar
```sh
docker exec -it docker_db_container bash
```
* Instalar client tools do psql
```sh
apt-get update
apt-cache search postgresql
apt-get install postgresql-client -y
```
* Entrando no psql e verificando a base
```sh
psql -h localhost -p 5432 -U postgres
\c investpess_ativo;
select * from ativoobjeto;
\q
exit
```
6. Rodar a aplicação utilizando a classe Inicializacao.Main
####
7. Para testar a aplicação:
* Baixar o software BloomRPC
* Carregar os arquivos proto que estão localizados em src\main\proto\...
* Apontar para o endereço 0.0.0.0:50051

#### Exemplos de json de cada endpoint gRPC:
* grpc.health.v1.Health
* / check
```sh
{
"service": "com.cyrillo.ativo.infra.entrypoint.servicogrpc.AtivoServerGRPC"
}
```
* /watch (não implementado)
####
* ativoobjetoproto.AtivoServerService
* / CadastraAtivoObjeto
```sh
{
  "ativo": {
    "sigla_ativo": "ITUB4",
    "nome_ativo": "ITAU UNIBANCO HOLDING S.A.",
    "descricao_cnpj_ativo": "60.872.504/0001-23",
    "tipo_ativo": 1
  }
}
```
* / ConsultaListaAtivo
```sh
{
"tipo_ativo": 1
}
```

## Recursos

##### Artigos
* Abordagem prática de Clean Architecture para microsserviço em Java https://blog.8thlight.com/uncle-bob/2012/08/13/the-clean-architecture.html

## Contatos

* Email: lccyrillo@gmail.com  
* Linkedin: [Luciano Cyrillo](https://www.linkedin.com/in/luciano-cyrillo/)
