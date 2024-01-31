package fuse.server;

import fuse.events.EventManager;
import fuse.player.FusePlayerManager;
import fuse.plugins.PluginManager;
import net.minestom.server.MinecraftServer;
import net.minestom.server.event.Event;
import net.minestom.server.event.EventNode;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.extras.MojangAuth;

public class FuseServer {
    // Minestorm server
    private MinecraftServer server;
    private GlobalEventHandler eventHandler;
    private EventNode<Event> eventNode;

    // Fuse server
    private FuseServerConfig config;
    private EventManager eventManager;
    private FusePlayerManager playerManager;
    private PluginManager pluginManager;

    public FuseServer(FuseServerConfig config) {
        this.server = MinecraftServer.init();
        this.config = config;

        MinecraftServer.setCompressionThreshold(config.compressionThreshold);
        MinecraftServer.setBrandName("FuseMC Server");

        this.eventManager = new EventManager();
        this.eventHandler = MinecraftServer.getGlobalEventHandler();
        this.eventNode = EventNode.all("internal");
        this.eventHandler.addChild(this.eventNode);
        this.playerManager = new FusePlayerManager();
        this.pluginManager = new PluginManager(this);
    }

    // Getters
    public FuseServerConfig getConfig() {
        return this.config;
    }

    public EventManager getEventManager() {
        return this.eventManager;
    }

    public FusePlayerManager getPlayerManager() {
        return this.playerManager;
    }

    public PluginManager getPluginManager() {
        return this.pluginManager;
    }

    // Broadcast
    public void broadcastMessage(String message) {
        this.playerManager.getPlayers().forEach(player -> {
            player.sendMessage(message);
        });
    }

    // Start the server
    public void start() {
        FuseHandler handler = new FuseHandler(this);
        handler.register(this.eventNode);

        FuseTicker ticker = new FuseTicker();
        ticker.register(this.eventHandler);

        FuseInstanceManager instanceManager = new FuseInstanceManager();
        instanceManager.create();

        if (this.config.onlineMode) {
            MojangAuth.init();
        }

        server.start(this.config.bind, this.config.port);
    }
}
