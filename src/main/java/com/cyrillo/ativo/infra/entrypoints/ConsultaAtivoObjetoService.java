package com.cyrillo.ativo.infra.entrypoints;

import proto.ativo.ativoobjetoproto.AtivoObjeto;
import proto.ativo.ativoobjetoproto.ConsultaAtivoObjetoRequest;
import proto.ativo.ativoobjetoproto.ConsultaAtivoObjetoResponse;
import proto.ativo.ativoobjetoproto.ConsultaAtivoObjetoServiceGrpc;
import io.grpc.stub.StreamObserver;

import java.sql.*;
import java.util.Properties;

public class ConsultaAtivoObjetoService  extends ConsultaAtivoObjetoServiceGrpc.ConsultaAtivoObjetoServiceImplBase {
    @Override
    public void consultaAtivoObjeto(ConsultaAtivoObjetoRequest request, StreamObserver<ConsultaAtivoObjetoResponse> responseObserver) {

        String sigla_ativo = request.getSiglaAtivo();

        // consulta no banco postgresql
        Connection c = null;
        Statement stmt = null;

        try {

            String url = "jdbc:postgresql:db";
            Properties props = new Properties();
            props.setProperty("user", "postgres");
            props.setProperty("password", "postgres");
            props.setProperty("ssl", "true");
            Connection conn = DriverManager.getConnection(url, props);
            String sql = "SELECT * FROM ativoobjeto WHERE nome_ativo = '" +  sigla_ativo +"'";
            //Prepara a instrução SQL
            PreparedStatement ps = conn.prepareStatement(sql);
            //Executa a instrução SQL
            ps.execute();

            // Class.forName("org.postgresql.Driver");
            //db_host = os.getenv('DB_HOST', 'db')
            //        db_user = os.getenv('DB_USER', 'postgres')
            //        db_name = os.getenv('DB_NAME', 'sender')
            //        dsn = f'dbname={db_name} user={db_user} host={db_host}'
            //        # dsn = 'dbname=email_sender user=postgres host=db'
            //        self.conn = psycopg2.connect(dsn)

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        String result = "Ativo: " + sigla_ativo + " encontrado na base!";
        AtivoObjeto umAtivo = AtivoObjeto.newBuilder()
                .setSiglaAtivo(sigla_ativo)
                .setNomeAtivo("Teste Nome Ativo")
                .setTipoAtivo(1)
                .setDescricaoCnpjAtivo("00.000.000/0000-00")
                .build();

        ConsultaAtivoObjetoResponse response = ConsultaAtivoObjetoResponse.newBuilder()
                .setResponseCode("200")
                .setResponseMessage(result)
                .setAtivo(umAtivo)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

}
