package com.duo.benchmark.rpc.grpc;

import com.duo.benchmark.Properties;
import com.duo.benchmark.rpc.grpc.service.EchoService;
import io.grpc.netty.shaded.io.grpc.netty.NettyServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.net.InetSocketAddress;

/**
 * @author pythias
 * @since 2020/3/20
 */
public class Server {
    private final static Logger LOGGER = LoggerFactory.getLogger(Server.class);
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("configuration.xml");
        Properties properties = (Properties) context.getBean("benchmarkConfiguration");

        LOGGER.info("GRPC server started, listening on {}:{}", properties.serverHost, properties.serverPort);

        //ServerBuilder.forPort(port).addService(new EchoService()).build().start().awaitTermination();
        NettyServerBuilder.forAddress(new InetSocketAddress(properties.serverHost, properties.serverPort)).addService(new EchoService()).build().start().awaitTermination();
    }
}
