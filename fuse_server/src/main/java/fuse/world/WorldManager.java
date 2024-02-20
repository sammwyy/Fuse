package fuse.world;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fuse.config.FuseServerConfig.WorldProviderConfig;
import fuse.world.errors.WorldException;
import net.minestom.server.MinecraftServer;
import net.minestom.server.instance.IChunkLoader;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.InstanceManager;

public class WorldManager {
    private WorldLoader loader;
    private Map<String, World> worlds;

    public WorldManager(WorldProviderConfig config) {
        this.loader = WorldLoader.init(config);
        this.worlds = new HashMap<>();
    }

    public boolean existWorld(String worldName) {
        return this.loader.existWorld(worldName);
    }

    public World getWorld(String worldName) {
        return worlds.get(worldName);
    }

    public List<String> getAvailableWorlds() {
        return this.loader.getWorlds();
    }

    public Collection<World> getWorlds() {
        return worlds.values();
    }

    public World loadWorld(String worldName, InstanceContainer instance) throws WorldException {
        IChunkLoader chunkLoader = null;

        try {
            chunkLoader = this.loader.loadWorld(worldName);
        } catch (IOException e) {
            throw new WorldException(worldName, e);
        }

        World world = new World(worldName, instance, chunkLoader);
        worlds.put(worldName, world);
        world.init();
        return world;
    }

    public World loadWorld(String worldName) throws WorldException {
        InstanceManager instanceManager = MinecraftServer.getInstanceManager();
        InstanceContainer container = instanceManager.createInstanceContainer();
        return loadWorld(worldName, container);
    }
}
