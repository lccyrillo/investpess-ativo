package com.cyrillo.ativo;

import com.proto.ativo.ativoobjetoproto.AtivoObjeto;
import com.proto.ativo.ativoobjetoproto.CadastraAtivoObjetoRequest;
import com.proto.ativo.ativoobjetoproto.CadastraAtivoObjetoResponse;
import com.proto.ativo.ativoobjetoproto.CadastraAtivoObjetoServiceGrpc;
import io.grpc.stub.StreamObserver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CadastraAtivoObjetoServiceImpl extends CadastraAtivoObjetoServiceGrpc.CadastraAtivoObjetoServiceImplBase {
    @Override
    public void cadastraAtivoObjeto(CadastraAtivoObjetoRequest request, StreamObserver<CadastraAtivoObjetoResponse> responseObserver) {
        // super.cadastraAtivoObjeto(request, responseObserver);
        String msgErro = "sem erro";
        System.out.println("iniciando cadastra ativo objeto");
        AtivoObjeto ativo = request.getAtivo();
        String sigla_ativo = ativo.getSiglaAtivo();
        String nome_ativo = ativo.getNomeAtivo();
        String descricao_cnpj_ativo = ativo.getDescricaoCnpjAtivo();
        int tipo_ativo = ativo.getTipoAtivo();


        // consulta no banco postgresql

        try {

            System.out.println("entrou no try");


            String db_host = System.getenv("DB_HOST");
            String db_port = System.getenv("DB_PORT");

            if (db_host == null) {
                db_host = "localhost"; //ambiente local
            }
            if (db_port == null) {
                db_port = "5433"; // ambiente local
            }
            String url = "jdbc:postgresql://" + db_host + ":" + db_port + "/investpess_ativo";
            System.out.println("URL" + url);

            // produção
            //String url = "jdbc:postgresql://db:5432/investpess_ativo";
            // dev
            //String url = "jdbc:postgresql://localhost:5433/investpess_ativo";

            Class.forName("org.postgresql.Driver");
            //Connection conn = DriverManager.getConnection(url, props);
            Connection conn = DriverManager.getConnection(url, "postgres",null);
            //DriverManager.getConnection("jdbc:postgresql://teste-postgres-compose:15432/postgres", "postgres", "Postgres2019!");

            System.out.println("pegou conexao");
            String sql = "INSERT INTO ativoobjeto (sigla_ativo,nome_ativo,descricao_cnpj_ativo,tipo_ativo)";
            sql = sql + " VALUES ('" + sigla_ativo + "','" + nome_ativo + "','" + descricao_cnpj_ativo + "'," + String.valueOf(tipo_ativo)+")";
            //Prepara a instrução SQL
            PreparedStatement ps = conn.prepareStatement(sql);
            System.out.println("preparou sql");
            //Executa a instrução SQL
            ps.execute();
            System.out.println("executou");

            // Class.forName("org.postgresql.Driver");
            //db_host = os.getenv('DB_HOST', 'db')
            //        db_user = os.getenv('DB_USER', 'postgres')
            //        db_name = os.getenv('DB_NAME', 'sender')
            //        dsn = f'dbname={db_name} user={db_user} host={db_host}'
            //        # dsn = 'dbname=email_sender user=postgres host=db'
            //        self.conn = psycopg2.connect(dsn)

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
            msgErro = throwables.getMessage();
        }



        String result = "Ativo: " + nome_ativo + " cadastrado!" + msgErro;
        CadastraAtivoObjetoResponse response = CadastraAtivoObjetoResponse.newBuilder()
                .setResponseCode("200")
                .setResponseMessage(result)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}