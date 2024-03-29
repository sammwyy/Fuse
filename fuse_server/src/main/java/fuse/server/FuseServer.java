package fuse.server;

import fuse.config.FuseServerConfig;
import fuse.events.EventManager;
import fuse.player.PlayerManager;
import fuse.plugins.PluginManager;
import fuse.services.ServiceManager;
import fuse.world.WorldManager;
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
    private boolean headless;
    private FuseServerConfig config;
    private EventManager eventManager;
    private PlayerManager playerManager;
    private PluginManager pluginManager;
    private ServiceManager serviceManager;
    private WorldManager worldManager;

    public FuseServer(FuseServerConfig config, boolean headless) {
        this.server = MinecraftServer.init();
        this.headless = headless;
        this.config = config;

        MinecraftServer.setCompressionThreshold(config.server.compression_threshold);
        MinecraftServer.setBrandName("FuseMC Server");

        this.eventManager = new EventManager();
        this.eventHandler = MinecraftServer.getGlobalEventHandler();
        this.eventNode = EventNode.all("internal");
        this.eventHandler.addChild(this.eventNode);
        this.playerManager = new PlayerManager();
        this.pluginManager = new PluginManager(this);
        this.serviceManager = new ServiceManager();
        this.worldManager = new WorldManager(config.worlds);
    }

    // Getters
    public FuseServerConfig getConfig() {
        return this.config;
    }

    public EventManager getEventManager() {
        return this.eventManager;
    }

    public PlayerManager getPlayerManager() {
        return this.playerManager;
    }

    public PluginManager getPluginManager() {
        return this.pluginManager;
    }

    public ServiceManager getServiceManager() {
        return this.serviceManager;
    }

    public WorldManager getWorldManager() {
        return this.worldManager;
    }

    public boolean isHeadless() {
        return this.headless;
    }

    // Broadcast
    public void broadcastMessage(String message) {
        this.playerManager.getPlayers().forEach(player -> {
            player.sendMessage(message);
        });
    }

    // Start the server
    public void start() throws Exception {
        FuseHandler handler = new FuseHandler(this);
        handler.register(this.eventNode);

        FuseTicker ticker = new FuseTicker();
        ticker.register(this.eventHandler);

        this.worldManager.loadWorld(this.config.worlds.default_world);

        if (this.config.server.online_mode) {
            MojangAuth.init();
        }

        server.start(this.config.server.bind, this.config.server.port);
    }
}
