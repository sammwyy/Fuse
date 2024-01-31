package fuse.world.loaders;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fuse.world.SlimeLoader;
import fuse.world.errors.UnknownWorldException;
import fuse.world.errors.WorldInUseException;

public class FileLoader implements SlimeLoader {
    private static final FilenameFilter WORLD_FILE_FILTER = (dir, name) -> name.endsWith(".slime");

    private final Map<String, RandomAccessFile> worldFiles;
    private final File worldsDir;

    public FileLoader(File worldsDir) {
        this.worldFiles = new HashMap<>();
        this.worldsDir = worldsDir;

        if (worldsDir.exists() && !worldsDir.isDirectory()) {
            throw new IllegalArgumentException("Worlds directory must be a directory");
        }

        worldsDir.mkdirs();
    }

    @Override
    public boolean isWorldExist(String worldName) throws IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isWorldExist'");
    }

    @Override
    public byte[] loadWorld(String worldName, boolean readOnly)
            throws UnknownWorldException, WorldInUseException, IOException {
        if (this.isWorldExist(worldName)) {
        }
    }

    @Override
    public List<String> listWorlds() throws IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listWorlds'");
    }

    @Override
    public void saveWorld(String worldName, byte[] worldData) throws IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveWorld'");
    }

    @Override
    public void unlockWorld(String worldName) throws UnknownWorldException, IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'unlockWorld'");
    }

    @Override
    public boolean isWorldLocked(String worldName) throws UnknownWorldException, IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isWorldLocked'");
    }

    @Override
    public void deleteWorld(String worldName) throws UnknownWorldException, IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteWorld'");
    }

}
