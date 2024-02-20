package fuse.config;

import java.io.File;
import java.io.IOException;

import com.moandjiezana.toml.TomlWriter;

public class Configuration {
    public void save(File file) throws IOException {
        TomlWriter writer = new TomlWriter();
        writer.write(this, file);
    }
}
