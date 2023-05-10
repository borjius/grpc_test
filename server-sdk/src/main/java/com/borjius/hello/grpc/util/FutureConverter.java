package com.borjius.hello.grpc.util;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class FutureConverter {

    private static final Executor EXECUTOR = Executors.newFixedThreadPool(10);

    /**
     * Converts a ListenableFuture to a CompletableFuture.
     * @param listenableFuture listenableFuture
     * @return completableFuture
     * @param <T> any type
     */
    public static <T> CompletableFuture<T> convertToCompletable(final ListenableFuture<T> listenableFuture) {
        final CompletableFuture<T> completable = new CompletableFuture<T>() {
            @Override
            public boolean cancel(final boolean mayInterruptIfRunning) {
                // propagate cancel to the listenable future
                final boolean result = listenableFuture.cancel(mayInterruptIfRunning);
                super.cancel(mayInterruptIfRunning);
                return result;
            }
        };

        // add callback
        Futures.addCallback(listenableFuture, new FutureCallback<T>() {
            @Override
            public void onSuccess(final T result) {
                completable.complete(result);
            }

            @Override
            public void onFailure(final Throwable t) {
                completable.completeExceptionally(t);
            }
        }, EXECUTOR);
        return completable;
    }
}
