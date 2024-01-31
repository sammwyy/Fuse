package fuse.server;

import net.minestom.server.MinecraftServer;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.InstanceManager;
import net.minestom.server.world.DimensionType;

public class FuseInstanceManager {
    private InstanceManager instanceManager;
    private InstanceContainer instanceContainer;

    public FuseInstanceManager() {
        this.instanceManager = MinecraftServer.getInstanceManager();
    }

    public void create() {
        this.instanceContainer = this.instanceManager.createInstanceContainer(DimensionType.OVERWORLD);
    }
}
