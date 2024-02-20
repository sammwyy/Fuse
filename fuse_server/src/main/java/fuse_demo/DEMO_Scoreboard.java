package fuse_demo;

import fuse.events.player.PlayerJoinEvent;
import fuse.plugins.EmbeddedPlugin;
import fuse.plugins.Plugin;
import net.kyori.adventure.text.Component;
import net.minestom.server.scoreboard.Sidebar;
import net.minestom.server.scoreboard.Sidebar.ScoreboardLine;

@EmbeddedPlugin(name = "DEMO_Scoreboard")
public class DEMO_Scoreboard extends Plugin {
    @Override
    public void onStart() {
        this.getEventListener().on(PlayerJoinEvent.class, (e) -> {
            Sidebar sidebar = new Sidebar(Component.text("Fuse Demo"));

            sidebar.createLine(new ScoreboardLine("test-1", Component.text("Test 1"), 10));
            sidebar.createLine(new ScoreboardLine("test-2", Component.text("Test 2"), 9));
            sidebar.createLine(new ScoreboardLine("test-3", Component.text("Test 3"), 8));
            sidebar.createLine(new ScoreboardLine("test-4", Component.text("Test 4"), 7));

            sidebar.addViewer(e.getPlayer());
        });
    }
}
