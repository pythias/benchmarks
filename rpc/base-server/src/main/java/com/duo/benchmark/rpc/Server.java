package com.duo.benchmark.rpc;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author pythias
 * @since 2020/3/18
 */
public class Server {
    public static void main(String[] args) throws InterruptedException {
        try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("server.xml")) {
            context.start();
            Thread.sleep(Integer.MAX_VALUE);
        }
    }
}
