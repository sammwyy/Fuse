package fuse.plugins;

import dev.rollczi.litecommands.LiteCommandsBuilder;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.minestom.LiteMinestomFactory;
import dev.rollczi.litecommands.minestom.LiteMinestomSettings;
import fuse.events.EventListenerGroup;
import fuse.plugins.handlers.FuseInvalidUsageHandler;
import fuse.plugins.handlers.FusePermissionMessageHandler;
import fuse.server.FuseServer;
import net.minestom.server.command.CommandSender;

public class Plugin {
    private PluginMetadata metadata;
    private PluginManager manager;
    private PluginState state;
    private LiteCommandsBuilder<CommandSender, LiteMinestomSettings, ?> commands;

    protected void _init(PluginMetadata metadata, PluginManager manager) {
        if (this.metadata != null) {
            throw new RuntimeException("Plugin already initialized");
        }

        this.metadata = metadata;
        this.manager = manager;
        this.state = PluginState.UNLOADED;
        this.commands = LiteMinestomFactory.builder()
                .missingPermission(new FusePermissionMessageHandler())
                .invalidUsage(new FuseInvalidUsageHandler());

        if (this.manager.isPluginLoaded(this.getID())) {
            throw new RuntimeException("Plugin " + metadata.name + " already loaded");
        }

        this.onInit();
    }

    protected void setInternalState(PluginState state) {
        this.state = state;
    }

    protected PluginState getInternalState() {
        return this.state;
    }

    protected LiteCommandsBuilder<CommandSender, LiteMinestomSettings, ?> getCommands() {
        return this.commands;
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

    public void registerCommand(Object obj) {
        if (obj.getClass().isAnnotationPresent(Command.class)) {
            this.commands.commands(obj);
        } else {
            throw new RuntimeException("Object " + obj.getClass().getName() + " is not a command");
        }
    }

    // Plugin events
    public void onInit() {
    }

    public void onStart() {
    }

    public void onStop() {
    }
}
