package fuse.world;

import net.minestom.server.instance.IChunkLoader;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.InstanceContainer;

public class World {
    private String name;
    private InstanceContainer instance;
    private IChunkLoader loader;

    public World(String name, InstanceContainer instance, IChunkLoader loader) {
        this.name = name;
        this.instance = instance;
        this.loader = loader;
    }

    public String getName() {
        return name;
    }

    public Instance getInstance() {
        return instance;
    }

    public void init() {
        this.instance.setChunkLoader(this.loader);
    }
}
