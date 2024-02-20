package com.sammwy.fuse_example;

import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;

import fuse.events.player.PlayerJoinEvent;
import fuse.events.player.PlayerQuitEvent;
import fuse.i18n.I18n;
import fuse.plugins.Plugin;
import net.minestom.server.entity.Player;

// Commands
@Command(name = "hello")
class HelloCommand {
    @Execute
    public void execute(@Context Player player) {
        I18n.sendMessage(player, "ExamplePlugin:hello");
    }
}

public class FuseExamplePlugin extends Plugin {
    // Listeners.
    private void onPlayerJoin(PlayerJoinEvent e) {
        e.getPlayer().sendMessage("Hey, welcome to the server!");
        this.getServer().broadcastMessage(e.getPlayer().getName() + " joined the server!");
    }

    private void onPlayerQuit(PlayerQuitEvent e) {
        this.getServer().broadcastMessage(e.getPlayer().getName() + " left the server!");
    }

    @Override
    public void onStart() {
        System.out.println("FuseExamplePlugin has started!");

        this.getTranslations().add("en", "hello", "Hello, this is just an example!");
        this.getTranslations().add("es", "hello", "¡Hola, esto es solo un ejemplo!");

        this.getEventListener().on(PlayerJoinEvent.class, this::onPlayerJoin);
        this.getEventListener().on(PlayerQuitEvent.class, this::onPlayerQuit);

        this.registerCommand(new HelloCommand());
    }
}
