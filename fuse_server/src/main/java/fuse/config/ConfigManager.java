package fuse.config;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.moandjiezana.toml.Toml;

import fuse.utils.FileUtils;

public class ConfigManager {
    private File jarFile;
    private File dataDir;
    private Map<String, Toml> configs;
    private Map<String, Object> cache;

    public ConfigManager(File jarFile, File dataDir) {
        this.jarFile = jarFile;
        this.dataDir = dataDir;
        this.configs = new HashMap<>();
        this.cache = new HashMap<>();
    }

    public File getConfigFile(String fileName) {
        File file = new File(dataDir, fileName);

        if (!file.exists()) {
            try {
                String raw = FileUtils.getChildInJar(this.jarFile, fileName);
                FileUtils.writeToFile(file, raw);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return file;
    }

    public Toml getConfig(String fileName) {
        if (configs.containsKey(fileName)) {
            return configs.get(fileName);
        }

        File file = this.getConfigFile(fileName);
        Toml toml = new Toml().read(file);
        configs.put(fileName, toml);

        return toml;
    }

    @SuppressWarnings("unchecked")
    public <T> T getConfig(String fileName, Class<T> clazz) {
        if (cache.containsKey(fileName)) {
            Object value = cache.get(fileName);

            if (value.getClass().equals(clazz)) {
                return (T) value;
            } else {
                throw new RuntimeException("Invalid cache type");
            }
        }

        T config = getConfig(fileName).to(clazz);
        cache.put(fileName, config);
        return config;
    }
}
