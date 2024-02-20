package fuse_example_plugins;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import fuse.events.player.PlayerJoinEvent;
import fuse.events.player.PlayerQuitEvent;
import fuse.plugins.EmbeddedPlugin;
import fuse.plugins.Plugin;
import net.minestom.server.entity.Player;

@Command(name = "test")
class MyCommand {
    @Execute
    public void execute(@Context Player player, @Arg("text") String text) {
        player.sendMessage("You said: " + text);
    }
}

@EmbeddedPlugin(name = "MessageOnJoin", version = "1.0.0", description = "Sends a message to the player when they join the server.")
public class DEMO_MessageOnJoin extends Plugin {
    // DEMO events
    private void onPlayerJoin(PlayerJoinEvent e) {
        e.getPlayer().sendMessage("Hola como le va uwu");
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

        this.registerCommand(new MyCommand());
    }
}
