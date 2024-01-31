package fuse.plugins;

import fuse.events.EventListenerGroup;
import fuse.server.FuseServer;

public class Plugin {
    private PluginMetadata metadata;
    private PluginManager manager;
    private PluginState state;

    protected void _init(PluginMetadata metadata, PluginManager manager) {
        if (this.metadata != null) {
            throw new RuntimeException("Plugin already initialized");
        }

        this.metadata = metadata;
        this.manager = manager;
        this.state = PluginState.UNLOADED;

        if (this.manager.isPluginLoaded(this.getID())) {
            throw new RuntimeException("Plugin " + metadata.name + " already loaded");
        }
    }

    protected void setInternalState(PluginState state) {
        this.state = state;
    }

    protected PluginState getInternalState() {
        return this.state;
    }

    // Getters
    public PluginMetadata getMetaData() {
        return this.metadata;
    }

    public PluginManager getPluginManager() {
        return this.manager;
    }

    public FuseServer getServer() {
        return this.manager.getServer();
    }

    // Utils
    public String getID() {
        return this.metadata.name.toLowerCase().replace(" ", "-");
    }

    // Server events
    public EventListenerGroup getEventListener() {
        return this.getServer().getEventManager().getEventListener(this);
    }

    // Plugin events
    public void onStart() {
    }

    public void onStop() {
    }
}
