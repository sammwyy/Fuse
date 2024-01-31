package fuse.server;

import java.util.concurrent.atomic.AtomicReference;

import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.server.ServerTickMonitorEvent;
import net.minestom.server.monitoring.TickMonitor;

public class FuseTicker {
    private static final AtomicReference<TickMonitor> LAST_TICK = new AtomicReference<>();

    public void register(GlobalEventHandler eventHandler) {
        eventHandler.addListener(ServerTickMonitorEvent.class, event -> LAST_TICK.set(event.getTickMonitor()));
    }
}
