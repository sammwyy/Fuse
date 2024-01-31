
package fuse;

import fuse.server.FuseServer;
import fuse.server.FuseServerConfig;
import fuse_example_plugins.DEMO_MessageOnJoin;

public class App {
    public static void main(String[] args) {
        FuseServerConfig config = new FuseServerConfig();
        FuseServer server = new FuseServer(config);
        server.start();

        server.getPluginManager().loadPlugin(DEMO_MessageOnJoin.class);
    }
}
