package fuse.player;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.minestom.server.entity.Player;

public class PlayerManager {
    private Map<String, Player> nameIndex = new HashMap<>();
    private Map<String, Player> uuidIndex = new HashMap<>();

    public PlayerManager() {
        this.nameIndex = new HashMap<>();
        this.uuidIndex = new HashMap<>();
    }

    public void addPlayer(Player player) {
        this.nameIndex.put(player.getUsername().toLowerCase(), player);
        this.uuidIndex.put(player.getUuid().toString(), player);
    }

    public Collection<Player> getPlayers() {
        return this.nameIndex.values();
    }

    public Player getPlayer(String username) {
        return this.nameIndex.get(username.toLowerCase());
    }

    public Player getPlayer(UUID uuid) {
        return this.uuidIndex.get(uuid.toString());
    }

    public void removePlayer(Player player) {
        this.nameIndex.remove(player.getUsername().toLowerCase());
        this.uuidIndex.remove(player.getUuid().toString());
    }
}
