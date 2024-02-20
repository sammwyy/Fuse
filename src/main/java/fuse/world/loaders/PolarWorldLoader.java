package fuse.world.loaders;

import java.io.File;
import java.io.IOException;
import java.util.List;

import fuse.utils.FileUtils;
import fuse.world.WorldLoader;
import fuse.world.errors.WorldException;
import fuse.world.errors.WorldNotFoundException;
import net.hollowcube.polar.PolarLoader;
import net.minestom.server.instance.IChunkLoader;

public class PolarWorldLoader implements WorldLoader {
    private File path;

    public PolarWorldLoader(File path) {
        this.path = path;
    }

    public PolarWorldLoader(String path) {
        this(FileUtils.getCurrentChild(path));
    }

    public File getWorldFile(String worldName) {
        return new File(this.path, worldName + ".polar");
    }

    @Override
    public IChunkLoader loadWorld(String worldName) throws IOException, WorldException {
        File worldFile = this.getWorldFile(worldName);

        if (!worldFile.exists()) {
            throw new WorldNotFoundException(worldName);
        }

        return new PolarLoader(this.getWorldFile(worldName).toPath());
    }

    @Override
    public boolean existWorld(String worldName) {
        return getWorldFile(worldName).exists();
    }

    @Override
    public List<String> getWorlds() {
        return FileUtils.getFilesName(this.path, true);
    }

}
