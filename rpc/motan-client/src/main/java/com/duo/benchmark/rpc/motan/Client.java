package com.duo.benchmark.rpc.motan;

import com.duo.benchmark.rpc.Tools;
import com.weibo.api.motan.closable.ShutDownHook;
import com.weibo.api.motan.util.StatsUtil;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;

/**
 * @author pythias
 * @since 2020/3/18
 */
@State(Scope.Benchmark)
public class Client extends com.duo.benchmark.rpc.Client {
    @TearDown
    @Override
    public void close() {
        context.close();

        ShutDownHook.runHook(true);
        if (!StatsUtil.executorService.isShutdown()) {
            StatsUtil.executorService.shutdownNow();
        }
    }

    public static void main(String[] args) throws Exception {
        Tools.run(new Client(), args);
    }
}
