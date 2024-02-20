package fuse.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import fuse.utils.GenericRunnable;

public class EventListenerGroup {
    private Map<Class<? extends Event>, List<EventListener<?>>> listeners;

    public EventListenerGroup() {
        this.listeners = new HashMap<>();
    }

    public List<EventListener<?>> getListeners(Class<? extends Event> event) {
        List<EventListener<?>> list = this.listeners.get(event);
        return list;
    }

    public <T extends Event> void on(Class<T> event, EventListener<T> listener) {
        List<EventListener<?>> list = this.getListeners(event);

        if (list == null) {
            list = new ArrayList<>();
        }

        list.add(listener);
        list.sort((a, b) -> {
            return a.getPriority().compare(b.getPriority());
        });
        this.listeners.put(event, list);
    }

    // Runnable listener.
    public <T extends Event> void on(Class<T> event, EventListenerPriority priority,
            GenericRunnable<T> listener) {
        EventListener<T> eventListener = new EventListener<T>(priority, listener);
        this.on(event, eventListener);
    }

    public <T extends Event> void on(Class<T> event, GenericRunnable<T> listener) {
        this.on(event, EventListenerPriority.NORMAL, listener);
    }

    // Functional listener
    public <T extends Event> void on(Class<T> event, EventListenerPriority priority,
            Function<T, Void> listener) {
        EventListener<T> eventListener = new EventListener<T>(priority, listener);
        this.on(event, eventListener);
    }

    public <T extends Event> void on(Class<T> event, Function<T, Void> listener) {
        this.on(event, EventListenerPriority.NORMAL, listener);
    }

    // Emitting events.
    public boolean emit(Event event) {
        List<EventListener<?>> list = this.listeners.get(event.getClass());

        if (list == null) {
            return true;
        }

        for (EventListener<?> listener : list) {
            if (!listener.call(event)) {
                return false;
            }
        }

        return true;
    }
}
