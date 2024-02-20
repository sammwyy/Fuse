package fuse.plugins;

import java.io.File;
import java.io.IOException;

import com.moandjiezana.toml.Toml;

import fuse.utils.FileUtils;

public class PluginMetadata {
    public String name;
    public String main;
    public String version;
    public String description = null;
    public String[] authors = new String[0];
    public String[] dependencies = new String[0];
    public String[] soft_dependencies = new String[0];
    public String website = null;

    public static PluginMetadata readFrom(File file) {
        String raw = null;

        try {
            raw = FileUtils.getChildInJar(file, "plugin.toml");
        } catch (IOException ignored) {
            try {
                raw = FileUtils.getChildInJar(file, "fuse.toml");
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        Toml toml = new Toml().read(raw);
        return toml.to(PluginMetadata.class);
    }
}
