package fuse_example_plugins;

import fuse.events.player.PlayerJoinEvent;
import fuse.events.player.PlayerQuitEvent;
import fuse.plugins.EmbeddedPlugin;
import fuse.plugins.Plugin;

@EmbeddedPlugin(name = "MessageOnJoin", version = "1.0.0", description = "Sends a message to the player when they join the server.")
public class DEMO_MessageOnJoin extends Plugin {
    // DEMO events
    private void onPlayerJoin(PlayerJoinEvent e) {
        e.getPlayer().sendMessage("Hello, this is just an example!");
        this.getServer().broadcastMessage(e.getPlayer().getName() + " joined the server!");
    }

    private void onPlayerQuit(PlayerQuitEvent e) {
        this.getServer().broadcastMessage(e.getPlayer().getName() + " left the server!");
    }

    // Entry point
    @Override
    public void onStart() {
        this.getEventListener().on(PlayerJoinEvent.class, this::onPlayerJoin);
        this.getEventListener().on(PlayerQuitEvent.class, this::onPlayerQuit);
    }
}
