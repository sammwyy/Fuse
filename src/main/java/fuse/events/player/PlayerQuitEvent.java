package fuse.events.player;

import fuse.events.Event;
import net.minestom.server.entity.Player;

public class PlayerQuitEvent extends Event {
    private Player player;

    public PlayerQuitEvent(Player player) {
        super();
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
