package fuse.plugins.loader;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import fuse.plugins.Plugin;
import fuse.plugins.PluginManager;
import fuse.plugins.PluginMetadata;
import fuse.plugins.errors.InvalidPluginException;

public class PluginLoader {
    private final Pattern[] fileFilters = new Pattern[] { Pattern.compile("\\.jar$"), };
    private final Map<String, Class<?>> classes = new HashMap<String, Class<?>>();
    private final Map<String, PluginClassLoader> loaders = new LinkedHashMap<String, PluginClassLoader>();

    // Class manager.
    public Class<?> getClassByName(final String name) {
        Class<?> cachedClass = classes.get(name);

        if (cachedClass != null) {
            return cachedClass;
        } else {
            for (String current : loaders.keySet()) {
                PluginClassLoader loader = loaders.get(current);

                try {
                    cachedClass = loader.findClass(name, false);
                } catch (ClassNotFoundException ignored) {
                }
                if (cachedClass != null) {
                    return cachedClass;
                }
            }
        }
        return null;
    }

    public void setClass(final String name, final Class<?> clazz) {
        if (!classes.containsKey(name)) {
            classes.put(name, clazz);
        }
    }

    public void removeClass(String name) {
        classes.remove(name);
    }

    // Plugin loader.
    public List<Plugin> loadPlugins(File directory, PluginManager manager) {
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException("Not a directory: " + directory);
        }

        File[] files = directory.listFiles((dir, name) -> {
            for (Pattern filter : fileFilters) {
                if (filter.matcher(name).find()) {
                    return true;
                }
            }

            return false;
        });

        if (files == null) {
            return null;
        }

        for (File file : files) {
            try {
                loadPlugin(file, manager);
            } catch (InvalidPluginException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public Plugin loadPlugin(File file, PluginManager manager) throws InvalidPluginException {
        if (!file.exists()) {
            throw new InvalidPluginException(file.getName(), "File does not exist");
        }

        PluginMetadata metadata = PluginMetadata.readFrom(file);
        if (metadata == null) {
            throw new InvalidPluginException(file.getName(), "No plugin metadata found");
        }

        if (metadata.name == null) {
            throw new InvalidPluginException(file.getName(), "No name found");
        }

        if (metadata.main == null) {
            throw new InvalidPluginException(metadata.name, "No main class found");
        }

        if (metadata.version == null) {
            throw new InvalidPluginException(metadata.name, "No version found");
        }

        File parentFile = file.getParentFile();
        File dataFolder = new File(parentFile, metadata.name);

        for (String dependency : metadata.dependencies) {
            if (loaders == null || !loaders.containsKey(dependency)) {
                throw new InvalidPluginException(metadata.name, "Dependency not found: " + dependency);
            }
        }

        try (PluginClassLoader loader = new PluginClassLoader(
                this,
                getClass().getClassLoader(),
                manager,
                metadata,
                dataFolder,
                file)) {
            loaders.put(metadata.name, loader);
            manager.startPlugin(loader.plugin);
            return loader.plugin;
        } catch (IOException e) {
            throw new InvalidPluginException(metadata.name, "Error loading plugin", e);
        }
    }
}
