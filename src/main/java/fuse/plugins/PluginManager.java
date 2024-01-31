package fuse.plugins;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import fuse.events.EventManager;
import fuse.server.FuseServer;

public class PluginManager {
    private Map<String, Plugin> plugins;
    private FuseServer server;

    public PluginManager(FuseServer server) {
        this.plugins = new HashMap<>();
        this.server = server;
    }

    public Collection<Plugin> getPlugins() {
        return this.plugins.values();
    }

    public FuseServer getServer() {
        return this.server;
    }

    public Plugin getPlugin(String name) {
        return this.plugins.get(name);
    }

    public boolean isPluginLoaded(String name) {
        return this.plugins.containsKey(name);
    }

    public void loadPlugin(PluginMetadata data) {
        Plugin plugin = null;
        EventManager eventManager = this.server.getEventManager();

        try {
            // Initialize plugin class.
            Class<?> clazz = Class.forName(data.mainClass);
            plugin = (Plugin) clazz.getConstructors()[0].newInstance();

            // Initialize plugin state.
            plugin._init(data, this);

            // Put plugin on the map.
            this.plugins.put(plugin.getID(), plugin);

            // Register plugin in event manager.
            eventManager.registerPlugin(plugin);

            // Trying to start plugin.
            plugin.onStart();
            System.out.println("Loaded plugin " + data.name);
        } catch (Exception e) {
            System.out.println("Failed to load plugin " + data.name);
            e.printStackTrace();

            // If plugin failed to load, set state to crashed.
            if (plugin != null) {
                plugin.setInternalState(PluginState.CRASHED);

                if (eventManager.isPluginRegistered(plugin)) {
                    eventManager.unregisterPlugin(plugin);
                }
            }
        }
    }

    public void loadPlugin(Class<?> clazz) {
        EmbeddedPlugin annotation = clazz.getAnnotation(EmbeddedPlugin.class);

        if (annotation == null) {
            System.out.println("Failed to load plugin " + clazz.getName() + ": no @EmbeddedPlugin annotation found");
            return;
        }

        PluginMetadata data = new PluginMetadata();
        data.name = annotation.name();
        data.version = annotation.version();
        data.description = annotation.description();
        data.authors = annotation.authors();
        data.dependencies = annotation.dependencies();
        data.softDependencies = annotation.softDependencies();
        data.mainClass = clazz.getName();

        this.loadPlugin(data);
    }

    public void unloadPlugin(Plugin plugin, boolean removeFromList) {
        // Unregister plugin from event manager.
        EventManager eventManager = this.server.getEventManager();
        eventManager.unregisterPlugin(plugin);

        // Remove plugin from map.
        plugin.setInternalState(PluginState.UNLOADED);

        // Remove plugin from list.
        if (removeFromList) {
            this.plugins.remove(plugin.getID());
        }

        try {
            // Trying to stop plugin.
            plugin.onStop();
            System.out.println("Unloaded plugin " + plugin.getMetaData().name);
        } catch (Exception e) {
            // If plugin failed to stop, set state to crashed.
            plugin.setInternalState(PluginState.CRASHED);
            System.out.println("Failed to stop plugin " + plugin.getMetaData().name);
        }
    }

    public void unloadPlugin(Plugin plugin) {
        this.unloadPlugin(plugin, true);
    }
}
