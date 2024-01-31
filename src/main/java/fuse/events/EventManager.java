package fuse.events;

import java.util.HashMap;
import java.util.Map;

import fuse.plugins.Plugin;

public class EventManager {
    private Map<Plugin, EventListenerGroup> listeners;

    public EventManager() {
        this.listeners = new HashMap<>();
    }

    public void registerPlugin(Plugin plugin) {
        if (this.listeners.containsKey(plugin)) {
            throw new RuntimeException("Plugin already registered");
        }

        this.listeners.put(plugin, new EventListenerGroup());
    }

    public EventListenerGroup getEventListener(Plugin plugin) {
        if (!this.listeners.containsKey(plugin)) {
            throw new RuntimeException("Plugin not registered");
        }

        return this.listeners.get(plugin);
    }

    public void unregisterPlugin(Plugin plugin) {
        if (!this.listeners.containsKey(plugin)) {
            throw new RuntimeException("Plugin not registered");
        }

        this.listeners.remove(plugin);
    }

    public boolean isPluginRegistered(Plugin plugin) {
        return this.listeners.containsKey(plugin);
    }

    public boolean emit(Event event) {
        for (EventListenerGroup group : this.listeners.values()) {
            if (!group.emit(event)) {
                return false;
            }
        }

        return true;
    }
}
