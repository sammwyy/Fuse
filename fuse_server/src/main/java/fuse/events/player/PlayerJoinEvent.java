package fuse.events.player;

import fuse.events.CancellableEvent;
import net.minestom.server.entity.Player;

public class PlayerJoinEvent extends CancellableEvent {
    private Player player;

    public PlayerJoinEvent(Player player) {
        super();
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
