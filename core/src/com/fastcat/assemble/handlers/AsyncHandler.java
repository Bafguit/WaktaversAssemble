package com.fastcat.assemble.handlers;

import com.google.common.util.concurrent.*;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

public final class AsyncHandler {

    @SuppressWarnings("null")
    private static final ListeningExecutorService service =
            MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());

    @SuppressWarnings("null")
    public static <T> void scheduleAsyncTask(Callable<T> task, FutureCallback<T> callback) {
        ListenableFuture<T> future = service.submit(task);
        Futures.addCallback(future, callback, service);
    }
}
