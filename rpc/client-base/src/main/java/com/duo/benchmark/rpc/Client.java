package com.duo.benchmark.rpc;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.ChainedOptionsBuilder;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author pythias
 * @since 2020/3/18
 */
public class Client {
    private final static Logger LOGGER = LoggerFactory.getLogger(Client.class);

    public static void main(String[] args) throws Exception {
        BenchmarkClient client = new BenchmarkClient();
        try {
            LOGGER.info("Service: {}",  client.echo());
        } catch (Exception e) {
            e.printStackTrace();
        }
        client.close();

        org.apache.commons.cli.Options options = new org.apache.commons.cli.Options();
        options.addOption(Option.builder().longOpt("warmupIterations").hasArg().build());
        options.addOption(Option.builder().longOpt("warmupTime").hasArg().build());
        options.addOption(Option.builder().longOpt("measurementIterations").hasArg().build());
        options.addOption(Option.builder().longOpt("measurementTime").hasArg().build());
        options.addOption(Option.builder().longOpt("forks").hasArg().build());
        options.addOption(Option.builder().longOpt("threads").hasArg().build());
        options.addOption(Option.builder().longOpt("batchSize").hasArg().build());
        options.addOption(Option.builder().longOpt("excludes").hasArg().build());
        options.addOption(Option.builder().longOpt("output").hasArg().build());

        CommandLineParser parser = new DefaultParser();
        CommandLine line = parser.parse(options, args);

        int warmupIterations = Integer.parseInt(line.getOptionValue("warmupIterations", "3"));
        int warmupTime = Integer.parseInt(line.getOptionValue("warmupTime", "10"));
        int measurementIterations = Integer.parseInt(line.getOptionValue("measurementIterations", "3"));
        int measurementTime = Integer.parseInt(line.getOptionValue("measurementTime", "10"));
        int forks = Integer.parseInt(line.getOptionValue("forks", "1"));
        int batchSize = Integer.parseInt(line.getOptionValue("batchSize", "1000"));
        int threads = Integer.parseInt(line.getOptionValue("threads", "32"));
        String output = line.getOptionValue("output", "");
        String[] excludes = line.getOptionValue("excludes", "").split(",");

        ChainedOptionsBuilder optBuilder = new OptionsBuilder()
                .include(Client.class.getSimpleName())
                .warmupIterations(warmupIterations)
                .warmupTime(TimeValue.seconds(warmupTime))
                .measurementIterations(measurementIterations)
                .measurementTime(TimeValue.seconds(measurementTime))
                .measurementBatchSize(batchSize)
                .threads(threads)
                .forks(forks);

        for (String exclude: excludes) {
            if (!exclude.isEmpty()) {
                optBuilder.exclude(exclude);
            }
        }

        if (!output.isEmpty()) {
            optBuilder.output(output);
        }

        Runner runner = new Runner(optBuilder.build());
        runner.run();
    }
}
