package com.cyrillo.ativo.infra.entrypoint.servicogrpc;

import com.cyrillo.ativo.infra.config.Sessao;
import io.grpc.Status;
import io.grpc.StatusException;
import io.grpc.health.v1.HealthCheckRequest;
import io.grpc.health.v1.HealthCheckResponse;
import io.grpc.health.v1.HealthGrpc.HealthImplBase;
import io.grpc.stub.StreamObserver;

import java.util.logging.Logger;

public class HealthCheckService extends HealthImplBase {
    private static final Logger logger = Logger.getLogger(HealthCheckService.class.getName());
    private static final String ACCEPTED_SERVICE = "com.cyrillo.ativo.infra.entrypoint.servicogrpc.AtivoServerGRPC";
    @Override
    public void check(HealthCheckRequest request, StreamObserver<HealthCheckResponse> responseObserver) {
        String service = request.getService();
        if (!service.equals(ACCEPTED_SERVICE)) {
            responseObserver.onError(
                    new StatusException(Status.NOT_FOUND.withDescription("Unknown service")));
        } else {
            HealthCheckResponse response = HealthCheckResponse.newBuilder().setStatus(getServingStatus()).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }
    private HealthCheckResponse.ServingStatus getServingStatus() {
        // TODO(partner): check the health status of the service
        // Bater nos repositórios da aplicação e ver se estão ok.
        HealthCheckResponse.ServingStatus status;
        try {
            Sessao dataProvider = new Sessao();
            if (dataProvider.healthCheckOk(dataProvider)) {
                status = HealthCheckResponse.ServingStatus.SERVING;
            }
            else {
                status = HealthCheckResponse.ServingStatus.NOT_SERVING;
            }
        }
        catch (Exception e) {
            status = HealthCheckResponse.ServingStatus.NOT_SERVING;
        }
        return status;
    }

    @Override
    public void watch(io.grpc.health.v1.HealthCheckRequest request,
                      io.grpc.stub.StreamObserver<io.grpc.health.v1.HealthCheckResponse> responseObserver) {
        // não implementado
        throw new UnsupportedOperationException("Not implemented yet");
    }

}