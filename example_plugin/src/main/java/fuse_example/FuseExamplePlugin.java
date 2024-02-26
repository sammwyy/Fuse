package fuse_example;

import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import fuse.config.Configuration;
import fuse.events.player.PlayerJoinEvent;
import fuse.events.player.PlayerQuitEvent;
import fuse.i18n.I18n;
import fuse.plugins.Plugin;
import net.kyori.adventure.text.Component;
import net.minestom.server.entity.Player;
import net.minestom.server.scoreboard.Sidebar;
import net.minestom.server.scoreboard.Sidebar.ScoreboardLine;

// Commands
@Command(name = "hello")
class HelloCommand {
    @Execute
    public void execute(@Context Player player) {
        I18n.sendMessage(player, "ExamplePlugin:category.greeting");
    }
}

// Configuration
class ExampleConfig extends Configuration {
    public String hello = "Testing message from config";
}

public class FuseExamplePlugin extends Plugin {
    // Listeners.
    private void onPlayerJoin(PlayerJoinEvent e) {
        e.getPlayer().sendMessage("Hey, welcome to the server!");
        this.getServer().broadcastMessage(e.getPlayer().getName() + " joined the server!");

        // Scoreboard.
        Sidebar sidebar = new Sidebar(Component.text("Fuse Demo"));

        sidebar.createLine(new ScoreboardLine("test-1", Component.text("Test 1"), 10));
        sidebar.createLine(new ScoreboardLine("test-2", Component.text("Test 2"), 9));
        sidebar.createLine(new ScoreboardLine("test-3", Component.text("Test 3"), 8));
        sidebar.createLine(new ScoreboardLine("test-4", Component.text("Test 4"), 7));

        sidebar.addViewer(e.getPlayer());
    }

    private void onPlayerQuit(PlayerQuitEvent e) {
        this.getServer().broadcastMessage(e.getPlayer().getName() + " left the server!");
    }

    @Override
    public void onStart() {
        ExampleConfig config = this.getConfig("config.toml", ExampleConfig.class);
        System.out.println("FuseExamplePlugin has started: " + config.hello);

        this.getEventListener().on(PlayerJoinEvent.class, this::onPlayerJoin);
        this.getEventListener().on(PlayerQuitEvent.class, this::onPlayerQuit);

        this.registerCommand(new HelloCommand());
    }
}
