package fuse.plugins;

import java.io.File;
import java.io.IOException;

import com.moandjiezana.toml.Toml;

import dev.rollczi.litecommands.LiteCommandsBuilder;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.minestom.LiteMinestomFactory;
import dev.rollczi.litecommands.minestom.LiteMinestomSettings;
import fuse.config.ConfigManager;
import fuse.events.EventListenerGroup;
import fuse.i18n.I18n;
import fuse.i18n.I18nContainer;
import fuse.plugins.handlers.FuseInvalidUsageHandler;
import fuse.plugins.handlers.FusePermissionMessageHandler;
import fuse.server.FuseServer;
import fuse.utils.PluginUtils;
import net.minestom.server.command.CommandSender;

public class Plugin {
    private PluginMetadata metadata;
    private PluginManager manager;
    private PluginState state;
    private LiteCommandsBuilder<CommandSender, LiteMinestomSettings, ?> commands;
    private I18nContainer translations;
    private File dataFolder;
    private File pluginFile;
    private ConfigManager configManager;

    protected void _init(PluginMetadata metadata, PluginManager manager, File dataFolder, File pluginFile) {
        if (this.metadata != null) {
            throw new RuntimeException("Plugin already initialized");
        }

        this.metadata = metadata;
        this.manager = manager;
        this.state = PluginState.UNLOADED;
        this.dataFolder = dataFolder;
        this.pluginFile = pluginFile;

        if (this.manager.isPluginLoaded(this.getID())) {
            throw new RuntimeException("Plugin " + metadata.name + " already loaded");
        }

        if (!this.dataFolder.exists() && !this.manager.getServer().isHeadless()) {
            this.dataFolder.mkdirs();
        }

        this.configManager = new ConfigManager(this.pluginFile, this.dataFolder);

        // Register commands
        this.commands = LiteMinestomFactory.builder()
                .missingPermission(new FusePermissionMessageHandler())
                .invalidUsage(new FuseInvalidUsageHandler());

        // Register translations
        try {
            this.translations = I18n.createContainer(getID(), true, PluginUtils.getTranslationFiles(this));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.onInit();
    }

    protected void setInternalState(PluginState state) {
        this.state = state;
    }

    protected PluginState getInternalState() {
        return this.state;
    }

    protected LiteCommandsBuilder<CommandSender, LiteMinestomSettings, ?> getCommands() {
        return this.commands;
    }

    // Getters
    public Toml getConfig(String file) {
        return this.configManager.getConfig(file);
    }

    public <T> T getConfig(String file, Class<T> clazz) {
        return this.configManager.getConfig(file, clazz);
    }

    public File getFile() {
        return this.pluginFile;
    }

    public File getDataFolder() {
        return this.dataFolder;
    }

    public PluginMetadata getMetaData() {
        return this.metadata;
    }

    public PluginManager getPluginManager() {
        return this.manager;
    }

    public FuseServer getServer() {
        return this.manager.getServer();
    }

    public I18nContainer getTranslations() {
        return this.translations;
    }

    // Utils
    public String getID() {
        return this.metadata.name.toLowerCase().replace(" ", "-");
    }

    // Server events
    public EventListenerGroup getEventListener() {
        return this.getServer().getEventManager().getEventListener(this);
    }

    public void registerCommand(Object obj) {
        if (obj.getClass().isAnnotationPresent(Command.class)) {
            this.commands.commands(obj);
        } else {
            throw new RuntimeException("Object " + obj.getClass().getName() + " is not a command");
        }
    }

    // Plugin events
    public void onInit() {
    }

    public void onStart() {
    }

    public void onStop() {
    }
}
