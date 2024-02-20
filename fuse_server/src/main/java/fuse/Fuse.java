
package fuse;

import java.io.File;

import fuse.config.FuseServerConfig;
import fuse.server.FuseServer;
import fuse.utils.FileUtils;
import fuse_example_plugins.DEMO_MessageOnJoin;
import fuse_example_plugins.DEMO_SayCommand;
import fuse_example_plugins.DEMO_Translatable;

public class Fuse {
    public static void launch(boolean headless) throws Exception {
        FuseServerConfig config = headless ? FuseServerConfig.fromEnv()
                : FuseServerConfig.load(FileUtils.getCurrentChild("config.toml"));

        FuseServer server = new FuseServer(config);

        // Plugin loader.
        File pluginsDir = FileUtils.getCurrentChild("plugins");

        if (!pluginsDir.exists() && !headless) {
            pluginsDir.mkdirs();
        }

        if (pluginsDir.exists()) {
            server.getPluginManager().loadPlugins(pluginsDir);
        }

        server.getPluginManager().loadEmbeddedPlugin(DEMO_MessageOnJoin.class);
        server.getPluginManager().loadEmbeddedPlugin(DEMO_SayCommand.class);
        server.getPluginManager().loadEmbeddedPlugin(DEMO_Translatable.class);

        server.start();
    }

    public static void safeLaunch(boolean headless) {
        try {
            Fuse.launch(headless);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
