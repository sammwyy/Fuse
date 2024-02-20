package fuse.events;

public enum EventListenerPriority {
    LOWEST(6),
    LOW(5),
    NORMAL(4),
    HIGH(3),
    HIGHEST(2),
    MONITOR(1);

    private int priority;

    EventListenerPriority(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return this.priority;
    }

    public int compare(EventListenerPriority priority) {
        return this.priority - priority.getPriority();
    }
}
