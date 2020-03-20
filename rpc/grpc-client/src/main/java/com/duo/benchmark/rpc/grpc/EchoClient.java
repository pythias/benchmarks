package com.duo.benchmark.rpc.grpc;

import com.duo.benchmark.rpc.grpc.service.EchoServiceGrpc;
import com.duo.benchmark.rpc.grpc.service.EchoServiceOuterClass;
import com.duo.benchmark.service.IEchoService;
import io.grpc.Channel;

/**
 * @author pythias
 * @since 2020/3/20
 */
public class EchoClient implements IEchoService, Cloneable {
    private final EchoServiceGrpc.EchoServiceBlockingStub blockingStub;

    public EchoClient(Channel channel) {
        blockingStub = EchoServiceGrpc.newBlockingStub(channel);
    }

    @Override
    public String echo(String message) {
        EchoServiceOuterClass.EchoRequest request = EchoServiceOuterClass.EchoRequest.newBuilder().setMessage(message).build();
        return blockingStub.echo(request).getMessage();
    }
}
