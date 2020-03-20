package com.duo.benchmark;

import java.util.List;

/**
 * @author pythias
 * @since 2020/3/20
 */
@lombok.Data
public class Properties {
    public String serverHost;
    public int serverPort;
    public int warmupIterations;
    public int warmupTime;
    public int measurementIterations;
    public int measurementTime;
    public int measurementBatchSize;
    public int forks;
    public int threads;
    public String output;
    public List<String> excludes;
}
