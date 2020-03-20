package com.duo.benchmark.rpc;

import com.duo.benchmark.Properties;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.ChainedOptionsBuilder;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author pythias
 * @since 2020/3/20
 */
public class Tools {
    private void start(AbstractClient client) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("configuration.xml");
        Properties properties = (Properties) context.getBean("benchmarkConfiguration");

        String className = client.getClass().getName();
        try {
            LOGGER.info("Service {} will started, echo: {}", className, client.echo());
        } catch (Exception e) {
            e.printStackTrace();
        }
        client.close();

        ChainedOptionsBuilder optBuilder = new OptionsBuilder()
                .include(className)
                .warmupIterations(properties.warmupIterations)
                .warmupTime(TimeValue.seconds(properties.warmupTime))
                .measurementIterations(properties.measurementIterations)
                .measurementTime(TimeValue.seconds(properties.measurementTime))
                .measurementBatchSize(properties.measurementBatchSize)
                .threads(properties.threads)
                .forks(properties.forks);

        for (String exclude: properties.excludes) {
            if (!exclude.isEmpty()) {
                optBuilder.exclude(exclude);
            }
        }

        if (!properties.output.isEmpty()) {
            optBuilder.output(properties.output);
        }

        Runner runner = new Runner(optBuilder.build());
        runner.run();
    }

    private final static Logger LOGGER = LoggerFactory.getLogger(Tools.class);

    public static void run(AbstractClient client, String[] args) throws Exception {
        new Tools().start(client);

        System.exit(1);
    }
}
