package fuse.plugins.loader;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import fuse.plugins.Plugin;
import fuse.plugins.PluginManager;
import fuse.plugins.PluginMetadata;
import fuse.plugins.errors.InvalidPluginException;

/**
 * A ClassLoader for plugins, to allow shared classes across multiple plugins
 */
final class PluginClassLoader extends URLClassLoader {
    private final PluginLoader loader;
    private final Map<String, Class<?>> classes = new HashMap<String, Class<?>>();
    private final PluginManager manager;
    private final PluginMetadata description;
    private final File dataFolder;
    private final File file;
    final Plugin plugin;
    private Plugin pluginInit;
    private IllegalStateException pluginState;

    @SuppressWarnings("deprecation")
    PluginClassLoader(
            final PluginLoader loader,
            final ClassLoader parent,
            final PluginManager manager,
            final PluginMetadata description,
            final File dataFolder,
            final File file) throws InvalidPluginException, MalformedURLException {
        super(new URL[] { file.toURI().toURL() }, parent);

        if (loader == null) {
            throw new IllegalArgumentException("Loader cannot be null");
        }

        String name = description.name;
        String mainClass = description.main;

        this.loader = loader;
        this.manager = manager;
        this.description = description;
        this.dataFolder = dataFolder;
        this.file = file;

        try {
            Class<?> jarClass;
            try {
                jarClass = Class.forName(mainClass, true, this);
            } catch (ClassNotFoundException ex) {
                throw new InvalidPluginException(name, "Cannot find main class `" + mainClass + "'", ex);
            }

            Class<? extends Plugin> pluginClass;
            try {
                pluginClass = jarClass.asSubclass(Plugin.class);
            } catch (ClassCastException ex) {
                throw new InvalidPluginException(name,
                        "main class `" + mainClass + "' does not extend JavaPlugin", ex);
            }

            plugin = pluginClass.newInstance();
            initialize(plugin);
        } catch (IllegalAccessException ex) {
            throw new InvalidPluginException(name, "No public constructor", ex);
        } catch (InstantiationException ex) {
            throw new InvalidPluginException(name, "Abnormal plugin type", ex);
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        return findClass(name, true);
    }

    public Class<?> findClass(String name, boolean checkGlobal) throws ClassNotFoundException {
        if (name.startsWith("net.minestom.") || name.startsWith("fuse.")) {
            throw new ClassNotFoundException(name);
        }
        Class<?> result = classes.get(name);

        if (result == null) {
            if (checkGlobal) {
                result = loader.getClassByName(name);
            }

            if (result == null) {
                result = super.findClass(name);

                if (result != null) {
                    loader.setClass(name, result);
                }
            }

            classes.put(name, result);
        }

        return result;
    }

    public Set<String> getClasses() {
        return classes.keySet();
    }

    synchronized void initialize(Plugin javaPlugin) {
        if (javaPlugin == null) {
            throw new IllegalArgumentException("Initializing plugin cannot be null");
        }

        if (javaPlugin.getClass().getClassLoader() != this) {
            throw new IllegalArgumentException("Cannot initialize plugin outside of this class loader");
        }

        if (this.plugin != null && this.pluginInit != null) {
            throw new IllegalArgumentException("Plugin already initialized!", pluginState);
        }

        pluginState = new IllegalStateException("Initial initialization");
        this.pluginInit = javaPlugin;

        try {
            Method initMethod = Plugin.class.getDeclaredMethod("_init", PluginMetadata.class, PluginManager.class,
                    File.class, File.class);
            initMethod.setAccessible(true);
            initMethod.invoke(javaPlugin, this.description, this.manager, this.dataFolder, this.file);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) {
            e.printStackTrace();
        }

    }
}