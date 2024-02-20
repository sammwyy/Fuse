package fuse.world;

import java.io.IOException;
import java.util.List;

import fuse.config.FuseServerConfig.WorldProviderConfig;
import fuse.world.errors.WorldException;
import fuse.world.loaders.PolarWorldLoader;
import net.minestom.server.instance.IChunkLoader;

public interface WorldLoader {
    public IChunkLoader loadWorld(String worldName) throws IOException, WorldException;

    public boolean existWorld(String worldName);

    public List<String> getWorlds();

    public static WorldLoader init(WorldProviderConfig config) {
        String type = config.type.toLowerCase();
        switch (type) {
            case "file:polar":
                return new PolarWorldLoader(config.path);
            default:
                throw new IllegalArgumentException("Invalid world provider type: " + type);
        }
    }
}
