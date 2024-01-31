package fuse.events.player;

import fuse.events.CancellableEvent;
import fuse.player.FusePlayer;

public class PlayerJoinEvent extends CancellableEvent {
    private FusePlayer player;

    public PlayerJoinEvent(FusePlayer player) {
        super();
        this.player = player;
    }

    public FusePlayer getPlayer() {
        return player;
    }
}
