package com.duo.benchmark.service;

/**
 * @author pythias
 * @since 2020/3/18
 */
public class EchoService implements IEchoService {
    @Override
    public String echo(String message) {
        return message;
    }
}
