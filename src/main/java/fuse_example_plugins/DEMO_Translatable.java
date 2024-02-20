package fuse_example_plugins;

import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import fuse.i18n.I18n;
import fuse.plugins.EmbeddedPlugin;
import fuse.plugins.Plugin;
import net.minestom.server.entity.Player;

@Command(name = "hello")
class HelloCommand {
    @Execute
    public void execute(@Context Player player) {
        I18n.sendMessage(player, "DEMO_Translatable:hello");
    }
}

@EmbeddedPlugin(name = "Translatable", version = "1.0.0", description = "An example of a translatable plugin.")
public class DEMO_Translatable extends Plugin {
    @Override
    public void onStart() {
        this.getTranslations().add("en", "hello", "Hello, this is just an example!");
        this.getTranslations().add("es", "hello", "¡Hola, esto es solo un ejemplo!");

        this.registerCommand(new HelloCommand());
    }
}
