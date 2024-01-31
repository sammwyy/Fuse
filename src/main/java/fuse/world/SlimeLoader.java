package fuse.world;

import java.io.IOException;
import java.util.List;

import fuse.world.errors.UnknownWorldException;
import fuse.world.errors.WorldInUseException;

public interface SlimeLoader {
    public boolean isWorldExist(String worldName) throws IOException;

    public byte[] loadWorld(String worldName, boolean readOnly)
            throws UnknownWorldException, WorldInUseException, IOException;

    public List<String> listWorlds() throws IOException;

    public void saveWorld(String worldName, byte[] worldData) throws IOException;

    public void unlockWorld(String worldName) throws UnknownWorldException, IOException;

    public boolean isWorldLocked(String worldName) throws UnknownWorldException, IOException;

    public void deleteWorld(String worldName) throws UnknownWorldException, IOException;
}
