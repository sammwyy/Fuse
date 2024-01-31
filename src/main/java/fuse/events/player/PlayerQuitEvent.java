package fuse.events.player;

import fuse.events.Event;
import fuse.player.FusePlayer;

public class PlayerQuitEvent extends Event {
    private FusePlayer player;

    public PlayerQuitEvent(FusePlayer player) {
        super();
        this.player = player;
    }

    public FusePlayer getPlayer() {
        return player;
    }
}
