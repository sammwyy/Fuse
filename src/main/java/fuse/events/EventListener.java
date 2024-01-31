package fuse.events;

import java.util.function.Function;

import fuse.utils.GenericRunnable;

public class EventListener<T extends Event> {
    private EventListenerPriority priority;
    private GenericRunnable<T> listener;

    public EventListener(EventListenerPriority priority, GenericRunnable<T> listener) {
        this.priority = priority;
        this.listener = listener;
    }

    public EventListener(EventListenerPriority priority, Function<T, Void> listener) {
        this.priority = priority;
        this.listener = new GenericRunnable<T>() {
            @Override
            public void run(T t) {
                listener.apply(t);
            }
        };
    }

    public EventListenerPriority getPriority() {
        return this.priority;
    }

    @SuppressWarnings("unchecked")
    public boolean call(Event event) {
        this.listener.run((T) event);

        if (event instanceof CancellableEvent) {
            CancellableEvent cancellable = (CancellableEvent) event;
            return !cancellable.isCancelled();
        }

        return true;
    }
}
