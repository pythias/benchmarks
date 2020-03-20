package com.duo.benchmark.rpc.grpc;

import com.duo.benchmark.Properties;
import com.duo.benchmark.rpc.AbstractClient;
import com.duo.benchmark.rpc.Tools;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.openjdk.jmh.annotations.*;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.TimeUnit;

/**
 * @author pythias
 * @since 2020/3/20
 */
@State(Scope.Benchmark)
public class Client extends AbstractClient {
    private final ManagedChannel channel;

    public Client() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("configuration.xml");
        Properties properties = (Properties) context.getBean("benchmarkConfiguration");
        ManagedChannelBuilder<?> channelBuilder = ManagedChannelBuilder.forAddress(properties.serverHost, properties.serverPort).usePlaintext();
        channel = channelBuilder.build();
        echoService = new EchoClient(channel);
    }

    @TearDown
    public void close() {
        try {
            channel.shutdown().awaitTermination(3, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Benchmark
    @BenchmarkMode({Mode.Throughput, Mode.AverageTime, Mode.SampleTime})
    @OutputTimeUnit(TimeUnit.SECONDS)
    @Override
    public String echo() throws Exception {
        return super.echo();
    }

    public static void main(String[] args) throws Exception {
        Tools.run(new Client(), args);
    }
}
