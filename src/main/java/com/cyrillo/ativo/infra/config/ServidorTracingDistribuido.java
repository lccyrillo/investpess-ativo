package com.cyrillo.ativo.infra.config;

import com.cyrillo.ativo.core.dataprovider.DataProviderInterface;
import io.opentracing.Tracer;
import io.opentracing.contrib.grpc.TracingServerInterceptor;
import io.opentracing.util.GlobalTracer;

public class ServidorTracingDistribuido {
    private DataProviderInterface data;
    private final Tracer tracer;

    private TracingServerInterceptor tracingServerInterceptor;

    public ServidorTracingDistribuido(DataProviderInterface dataProviderInterface) {
        this.data = dataProviderInterface;
        tracer = GlobalTracer.get();
        this.inicializaServidorTracingDistribuido();
    }

    private void inicializaServidorTracingDistribuido() {
        this.tracingServerInterceptor = TracingServerInterceptor
                .newBuilder()
                .withTracer(this.tracer)
                .build();
    }

    public TracingServerInterceptor geTtracingServerInterceptor() {
        return tracingServerInterceptor;
    }

}
