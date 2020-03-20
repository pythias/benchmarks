package com.duo.benchmark.rpc.grpc.service;

import io.grpc.stub.StreamObserver;

/**
 * @author pythias
 * @since 2020/3/20
 */
public class EchoService extends EchoServiceGrpc.EchoServiceImplBase {
    @Override
    public void echo(EchoServiceOuterClass.EchoRequest request, StreamObserver<EchoServiceOuterClass.EchoReply> responseObserver) {
        EchoServiceOuterClass.EchoReply reply = EchoServiceOuterClass.EchoReply.newBuilder().setMessage(request.getMessage()).build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
