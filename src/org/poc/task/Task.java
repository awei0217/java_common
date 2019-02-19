package org.poc.task;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

import org.poc.async.F;
import org.poc.async.F.Listenable;
import org.poc.eventbus.EventBus;
import org.poc.eventbus.EventResult;
import org.poc.eventbus.Sender;
import org.poc.task.handler.TaskHandler;
import org.poc.task.service.AcceptableService;
import org.poc.task.service.WorkingService;

/***
 * 
 * @author gaoqi3
 *
 */

public interface Task {

    enum Grade {
        Non, Init, Pending, Working, Waiting,Error
    }

    final TaskEvent.Acceptable AcceptWorkingFlag = new TaskEvent.Acceptable();
    
    final TaskEvent.Waiting WaitingFlg = new TaskEvent.Waiting() ;

    static Task generate(final EventBus eventbus) {
        return new Impl(eventbus);
    }

    static Task generate() {

        EventBus eb = EventBus.generate();

        eb.register(TaskEvent.Initializing.class, new TaskHandler.Initializing());

        eb.register(TaskEvent.Waiting.class, new TaskHandler.Waiting());

        eb.register(TaskEvent.Pending.class, new TaskHandler.Pending());

        eb.register(TaskEvent.Working.class, new TaskHandler.Working() {
            @Override
            protected <V> WorkingService getWorkingService(Optional<V> args) {
                return null;
            }
        });

        eb.register(TaskEvent.Errors.class, new TaskHandler.Errors());

        eb.register(TaskEvent.Acceptable.class, new TaskHandler.Acceptable() {

           
            @Override
            protected <V> AcceptableService getAcceptService(Optional<V> args) {
                return null;
            }
        });

        return generate(eb);
    }

    <T> Task start(T args);

    EventBus getEventBus();

    final class Impl implements Task {

        final Sender sender;

        final EventBus eb;

        Grade grade = Grade.Non;

        final TaskEvent.Waiting waitFlag = TaskEvent.generate(Grade.Waiting);

        final TaskEvent.Working workFlag = TaskEvent.generate(Grade.Working);

        final TaskEvent.Initializing initFlag = TaskEvent.generate(Grade.Init);

        public Impl(EventBus eb) {
            this.eb = eb;
            this.sender = eb.sender();
        }

        @Override
        public <T> Task start(T args) {
            changed(onBegin(args), args);
            return this;
        }

        private <V> Grade onBegin(V args) {
            Listenable<EventResult> result = null;
            switch (this.grade) {
            case Non:
                this.grade = Grade.Init;
                this.grade = onBegin(args);
                break;
            case Waiting:
                result = sender.send(waitFlag.withArgs(args));
                break;
            case Init:
                result = sender.send(initFlag.withArgs(args));// init
            default:
                break;
            }
            return Optional.ofNullable(result).map(r -> {
                EventResult er = EventResult.Ok ;
                try {
                    er = r.get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
                return er.isSuccessful() ? (Grade) er.getEntity() : this.grade;
            }).orElse(this.grade);
        }

        private <V> void changed(Grade grade, V args) {
            this.grade = grade;
            switch (grade) {
            case Pending:
                onPending(args);
                break;
            case Working:
                onWorking(args);
                break;
            default:
                break;
            }
        }

        private <V> void onPending(V args) {

            F.async(sender.send(new TaskEvent.Pending().withArgs(args)), new F.Callback<EventResult>() {

                @Override
                public void onFailure(Throwable e) {
                    onError(args, e);
                }

                @Override
                public void onSuccess(EventResult result) {
                    Impl.this.grade = Grade.Working;
                    onPendingNext(args, result);
                }

            });
        }

        private <V> void onWorking(V args) {

            F.async(sender.send(workFlag.withArgs(args)), new F.Callback<EventResult>() {

                @Override
                public void onSuccess(EventResult result) {
                    F.async(sender.send(new TaskEvent.Acceptable()), new F.Callback<EventResult>() {

                        @Override
                        public void onSuccess(EventResult result) {
                            // System.out.println(result.toString());
                            System.out.println("--" + result.getEntity().toString());
                        }

                        @Override
                        public void onFailure(Throwable t) {
                            onError(args, t);
                        }
                    });
                }

                @Override
                public void onFailure(Throwable t) {
                    onError(args, t);
                }
            });
        }

        private <V> void onError(V args, Throwable e) {

            F.async(sender.send(TaskEvent.generate(grade).withArgs(args)), new F.Callback<EventResult>() {

                @Override
                public void onSuccess(EventResult result) {
                    System.out.println("onError- " + result.toString());
                }

                @Override
                public void onFailure(Throwable t) {
                    System.out.println("--- on error " + t.getMessage());
                }
            });
        }

        private <V> void onPendingNext(V args, EventResult result) {

            System.out.println("-- Pending " + result.getEntity().toString());

            F.async(sender.send(TaskEvent.generate(grade).withArgs(args)), new F.Callback<EventResult>() {

                @Override
                public void onSuccess(EventResult result) {

                    F.async(sender.send(new TaskEvent.Acceptable().withArgs(args)), new F.Callback<EventResult>() {

                        @Override
                        public void onSuccess(EventResult result) {
                            System.out.println("-- Acceptable " + result.getEntity().toString());
                        }

                        @Override
                        public void onFailure(Throwable t) {
                            onError(args, t);
                        }
                    });
                }

                @Override
                public void onFailure(Throwable t) {
                    onError(args, t);
                }
            });
        }

        @Override
        public EventBus getEventBus() {
            return eb;
        }

    }

}
