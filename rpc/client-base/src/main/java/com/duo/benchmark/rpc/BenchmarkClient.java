package com.duo.benchmark.rpc;

import com.duo.benchmark.service.IEchoService;
import org.openjdk.jmh.annotations.*;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author pythias
 * @since 2020/3/19
 */
@State(Scope.Benchmark)
public class BenchmarkClient extends AbstractClient {
    private final ClassPathXmlApplicationContext context;

    public BenchmarkClient() {
        context = new ClassPathXmlApplicationContext("client.xml");
        context.start();
        echoService = (IEchoService) context.getBean("echoService");
    }

    @TearDown
    public void close() throws IOException {
        context.close();
    }

    @Benchmark
    @BenchmarkMode({Mode.Throughput, Mode.AverageTime, Mode.SampleTime})
    @OutputTimeUnit(TimeUnit.SECONDS)
    @Override
    public String echo() throws Exception {
        return super.echo();
    }
}
