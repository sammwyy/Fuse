package fuse_example_plugins;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import fuse.plugins.EmbeddedPlugin;
import fuse.plugins.Plugin;
import fuse.server.FuseServer;
import net.minestom.server.entity.Player;

@Command(name = "say")
class MyCommand {
    private FuseServer server;

    protected MyCommand(FuseServer server) {
        this.server = server;
    }

    @Execute
    public void execute(@Context Player player, @Arg("message") String message) {
        String username = player.getUsername();
        String text = String.format("Broadcast from %s: %s", username, message);
        server.broadcastMessage(text);
    }
}

@EmbeddedPlugin(name = "SayCommand", version = "1.0.0", description = "Create a command.")
public class DEMO_SayCommand extends Plugin {
    // Entry point
    @Override
    public void onStart() {
        this.registerCommand(new MyCommand(this.getServer()));
    }
}
