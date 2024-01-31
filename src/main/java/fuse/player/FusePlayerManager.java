package fuse.player;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.minestom.server.entity.Player;

public class FusePlayerManager {
    private Map<String, FusePlayer> nameIndex = new HashMap<>();
    private Map<String, FusePlayer> uuidIndex = new HashMap<>();
    private Map<Player, FusePlayer> nativeIndex = new HashMap<>();

    public FusePlayerManager() {
        this.nameIndex = new HashMap<>();
        this.uuidIndex = new HashMap<>();
        this.nativeIndex = new HashMap<>();
    }

    public FusePlayer wrapPlayer(Player player) {
        FusePlayer fusePlayer = new FusePlayer(player);
        this.nameIndex.put(player.getUsername().toLowerCase(), fusePlayer);
        this.uuidIndex.put(player.getUuid().toString(), fusePlayer);
        this.nativeIndex.put(player, fusePlayer);
        return fusePlayer;
    }

    public Collection<FusePlayer> getPlayers() {
        return this.nativeIndex.values();
    }

    public FusePlayer getPlayer(String username) {
        return this.nameIndex.get(username.toLowerCase());
    }

    public FusePlayer getPlayer(UUID uuid) {
        return this.uuidIndex.get(uuid.toString());
    }

    public FusePlayer getPlayer(Player player) {
        return this.nativeIndex.get(player);
    }

    public void removePlayer(Player player) {
        FusePlayer fusePlayer = this.nativeIndex.get(player);
        this.nameIndex.remove(player.getUsername().toLowerCase());
        this.uuidIndex.remove(player.getUuid().toString());
        this.nativeIndex.remove(player);
        fusePlayer.dispose();
    }
}
