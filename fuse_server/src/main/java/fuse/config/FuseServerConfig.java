package fuse.config;

import java.io.File;
import java.io.IOException;

import com.moandjiezana.toml.Toml;

public class FuseServerConfig extends Configuration {
    public class ServerConfig {
        public String bind = "0.0.0.0";
        public int port = 25565;
        public boolean online_mode = true;
        public int compression_threshold = 256;
    }

    public class ServerQuery {
        public int max_players = 20;
        public boolean hide_players = false;
        public String motd = "&dA Fuse Server";
        public String version = "1.20.4";
        public String[] playersSample = new String[] {};
    }

    public class WorldProviderConfig {
        public String type = "file:polar";
        public String path = "worlds";
        public String default_world = "world";
    }

    public ServerConfig server = new ServerConfig();
    public ServerQuery query = new ServerQuery();
    public WorldProviderConfig worlds = new WorldProviderConfig();

    // Utils functions.
    public static FuseServerConfig fromEnv() {
        FuseServerConfig config = new FuseServerConfig();

        if (System.getenv("FUSE_SERVER_BIND") != null) {
            config.server.bind = System.getenv("FUSE_SERVER_BIND");
        }

        if (System.getenv("FUSE_SERVER_PORT") != null) {
            config.server.port = Integer.parseInt(System.getenv("FUSE_SERVER_PORT"));
        }

        if (System.getenv("FUSE_SERVER_ONLINE_MODE") != null) {
            config.server.online_mode = Boolean.parseBoolean(System.getenv("FUSE_SERVER_ONLINE_MODE"));
        }

        if (System.getenv("FUSE_SERVER_COMPRESSION_THRESHOLD") != null) {
            config.server.compression_threshold = Integer.parseInt(System.getenv("FUSE_SERVER_COMPRESSION_THRESHOLD"));
        }

        if (System.getenv("FUSE_WORLD_PROVIDER_TYPE") != null) {
            config.worlds.type = System.getenv("FUSE_WORLD_PROVIDER_TYPE");
        }

        if (System.getenv("FUSE_WORLD_PROVIDER_PATH") != null) {
            config.worlds.path = System.getenv("FUSE_WORLD_PROVIDER_PATH");
        }

        if (System.getenv("FUSE_WORLD_DEFAULT_WORLD") != null) {
            config.worlds.default_world = System.getenv("FUSE_WORLD_DEFAULT_WORLD");
        }

        if (System.getenv("FUSE_QUERY_MAX_PLAYERS") != null) {
            config.query.max_players = Integer.parseInt(System.getenv("FUSE_QUERY_MAX_PLAYERS"));
        }

        if (System.getenv("FUSE_QUERY_HIDE_PLAYERS") != null) {
            config.query.hide_players = Boolean.parseBoolean(System.getenv("FUSE_QUERY_HIDE_PLAYERS"));
        }

        if (System.getenv("FUSE_QUERY_MOTD") != null) {
            config.query.motd = System.getenv("FUSE_QUERY_MOTD");
        }

        if (System.getenv("FUSE_QUERY_VERSION") != null) {
            config.query.version = System.getenv("FUSE_QUERY_VERSION");
        }

        if (System.getenv("FUSE_QUERY_PLAYERS_SAMPLE") != null) {
            config.query.playersSample = System.getenv("FUSE_QUERY_PLAYERS_SAMPLE").split(",");
        }

        return config;
    }

    public static FuseServerConfig load(File file) throws IOException {
        FuseServerConfig config = null;

        if (file.exists()) {
            config = new Toml().read(file).to(FuseServerConfig.class);
        } else {
            config = new FuseServerConfig();
            config.save(file);
        }

        return config;
    }
}
