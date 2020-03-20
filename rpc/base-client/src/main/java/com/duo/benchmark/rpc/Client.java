package com.duo.benchmark.rpc;

import com.duo.benchmark.service.IEchoService;
import org.openjdk.jmh.annotations.*;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.TimeUnit;

/**
 * @author pythias
 * @since 2020/3/18
 */
@State(Scope.Benchmark)
public class Client extends AbstractClient {
    protected final ClassPathXmlApplicationContext context;

    public Client() {
        context = new ClassPathXmlApplicationContext("client.xml");
        context.start();
        echoService = (IEchoService) context.getBean("echoService");
    }

    @TearDown
    public void close() {
        context.close();
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
