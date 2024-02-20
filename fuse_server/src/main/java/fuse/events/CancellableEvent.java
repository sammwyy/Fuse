package fuse.events;

public class CancellableEvent extends Event {
    private boolean cancelled;
    private String reason = null;

    public boolean isCancelled() {
        return this.cancelled;
    }

    public void cancel(String reason) {
        this.cancelled = true;
        this.reason = reason;
    }

    public void cancel() {
        this.cancel(null);
    }

    public String getCancelReason(String defaultReason) {
        if (this.reason == null) {
            return defaultReason;
        }

        return this.reason;
    }

    public String getCancelReason() {
        return this.getCancelReason(null);
    }
}
