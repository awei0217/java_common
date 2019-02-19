package org.poc.test.reactor3;

import java.util.Optional; 
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;
import org.poc.reactor.EventSource;
import org.poc.reactor.Transmission;
import org.poc.util.Timer;
import org.poc.reactor.EventSource.Event;

import reactor.core.publisher.FluxSink;
import reactor.core.scheduler.Schedulers;

public class TestEventSource {

    // @PatternMatch
    public void tsEmitterSource() {
        AtomicInteger limit = new AtomicInteger(10000);
        
        final EventSource.EmitterSource eSource = new EventSource.EmitterSource(new EventSource.EventEmitter.Impl() {

            @Override
            public void emit(long requestCount) {
                for (int i = 0; i < requestCount; i++) {
                    this.accept("xxxxx-" + i);
                }
            }
        }, FluxSink.OverflowStrategy.BUFFER) {

        };

        eSource.getStreams()

                .subscribe(new EventSource.AbsSubscriber() {

                    @Override
                    protected void onEvent(Event event) {

                        if (limit.decrementAndGet() <= 0) {
                            this.dispose();
                            eSource.getEmitter().dispose();
                        }
                        System.out.println("--x- " + limit.get() + "-" + event.getMessage().toString());
                    }

                    @Override
                    protected void onErrors(Throwable throwable) {

                    }

                });

        System.out.println("------------------------------------------");

    }

    @Test
    public void tsEmitterWithTransmission() {
        final Transmission transmission = Transmission.generate();

        new Thread() {
            @Override
            public void run() {

                int loop = 0;
                long start = System.nanoTime();
                int n = 1;
                while (true) {
                    loop++;
                    transmission.getGenerator().accept("Ts-" + n + "-" + loop);
                    if (loop % 10000 == 0) {
                        n++;
                        final long duration = System.nanoTime() - start;
                        final long ops = (loop * 1000L * 1000L * 1000L) / duration;
                        System.out.format("publish ops/sec       = %,d \n", ops);
                    }

                }
            }
        }.start();

        final long startTime = System.currentTimeMillis(); // 获取开始时间
        final EventSource.EmitterSource eSource = new EventSource.EmitterSource(new EventSource.EventEmitter.Impl() {

            final Transmission.Receiver receiver = transmission.getReceiver();

            @Override
            public void emit(long requestCount) {
                for (long i = 0; i < requestCount; i++) {
                    Optional.ofNullable(receiver.accept()).ifPresent(value -> this.accept(value));
                }
            }
        }, FluxSink.OverflowStrategy.BUFFER) {

        };

        eSource.getStreams()

                .publishOn(Schedulers.elastic())

                .subscribeOn(Schedulers.parallel())

                .map(v1 -> {
                    for (int i = 0; i < 100000; i++) {

                    }
                    return v1;
                })
                
//                .map(v2 -> {
//                    try {
//                        TimeUnit.MILLISECONDS.sleep(3);
//                    } catch (InterruptedException e1) {
//                        e1.printStackTrace();
//                    }
//                    return v2;
//                })
//
//                .map(v3 -> {
//                    try {
//                        TimeUnit.MILLISECONDS.sleep(5);
//                    } catch (InterruptedException e1) {
//                        e1.printStackTrace();
//                    }
//                    return v3;
//                })
//
//                .map(v4 -> {
//                    try {
//                        TimeUnit.MILLISECONDS.sleep(3);
//                    } catch (InterruptedException e1) {
//                        e1.printStackTrace();
//                    }
//                    return v4;
//                })

//                .map(v5 -> {
//                    try {
//                        TimeUnit.MILLISECONDS.sleep(1);
//                    } catch (InterruptedException e1) {
//                        e1.printStackTrace();
//                    }
//                    return v5;
//                })

                .subscribe(new EventSource.AbsSubscriber() {
                    long loop = 1;
                    long start = System.nanoTime();

                    @Override
                    protected void onEvent(Event event) {

                        // if (limit.decrementAndGet() <= 0) {
                        // this.dispose();
                        // eSource.getEmitter().dispose();
                        // }
                        loop++;
                        Object value = event.getMessage().toString();
                        if (loop % 10000 == 0) {
                            long endTime = System.currentTimeMillis(); // 获取开始时间
                            System.out.format("10000 ops/sec       = %s | %d |%s \n",
                                    Timer.exchange(endTime - startTime), loop, value);

                        } else {
                            if (loop % 200 == 0) {
                                final long duration = System.currentTimeMillis() - start;
                                System.out.format("Get ops/sec       = %s | %d \n", Timer.exchange(duration), loop);
                            }
                        }
                    }

                    @Override
                    protected void onErrors(Throwable throwable) {

                    }

                });

        System.out.println("------------------------------------------");
    }

}
