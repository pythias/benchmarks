package com.duo.benchmark.rpc;

import com.duo.benchmark.service.EchoService;
import com.duo.benchmark.service.IEchoService;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author pythias
 * @since 2020/3/18
 */
public abstract class AbstractClient {
    private final AtomicInteger counter = new AtomicInteger(0);
    protected IEchoService echoService = new EchoService();

    public String echo() throws Exception {
        String message = String.valueOf(counter.getAndIncrement());
        return echoService.echo(message);
    }
}
