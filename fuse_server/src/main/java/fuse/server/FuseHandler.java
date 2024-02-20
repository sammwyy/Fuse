package fuse.server;

import java.io.File;
import java.nio.file.Files;
import java.util.Base64;

import fuse.config.FuseServerConfig.ServerQuery;
import fuse.events.player.PlayerJoinEvent;
import fuse.events.player.PlayerQuitEvent;
import fuse.utils.FileUtils;
import net.kyori.adventure.text.Component;
import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;
import net.minestom.server.event.Event;
import net.minestom.server.event.EventNode;
import net.minestom.server.event.entity.EntityAttackEvent;
import net.minestom.server.event.item.ItemDropEvent;
import net.minestom.server.event.item.PickupItemEvent;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.event.player.PlayerBlockInteractEvent;
import net.minestom.server.event.player.PlayerBlockPlaceEvent;
import net.minestom.server.event.player.PlayerDeathEvent;
import net.minestom.server.event.player.PlayerDisconnectEvent;
import net.minestom.server.event.player.PlayerPacketEvent;
import net.minestom.server.event.player.PlayerPacketOutEvent;
import net.minestom.server.event.player.PlayerSpawnEvent;
import net.minestom.server.event.player.PlayerUseItemOnBlockEvent;
import net.minestom.server.event.server.ServerListPingEvent;
import net.minestom.server.instance.Instance;
import net.minestom.server.ping.ResponseData;
import net.minestom.server.utils.identity.NamedAndIdentified;

public class FuseHandler {
    private FuseServer server;
    private String favicon = null;

    public FuseHandler(FuseServer server) {
        this.server = server;

        File faviconFile = FileUtils.getCurrentChild("server-icon.png");
        if (faviconFile.exists()) {
            try {
                byte[] fileContent = Files.readAllBytes(faviconFile.toPath());
                String encodedString = Base64.getEncoder().encodeToString(fileContent);
                this.favicon = "data:image/png;base64," + encodedString;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    void onPlayerSpawn(PlayerSpawnEvent e) {
        Player player = e.getPlayer();

        if (e.isFirstSpawn()) {
            PlayerJoinEvent event = new PlayerJoinEvent(player);
            this.server.getEventManager().emit(event);

            if (event.isCancelled()) {
                if (player.getPlayerConnection().isOnline()) {
                    player.kick(event.getCancelReason("Disconnected (Join Event Cancelled)"));
                }

                return;
            }

            this.server.getPlayerManager().addPlayer(player);
        }

        player.setGameMode(GameMode.CREATIVE);
    }

    void onAsyncPlayerConfiguration(AsyncPlayerConfigurationEvent e) {
        Player player = e.getPlayer();
        var instances = MinecraftServer.getInstanceManager().getInstances();
        Instance instance = instances.stream().findFirst().get();
        e.setSpawningInstance(instance);
        player.setRespawnPoint(new Pos(0, 40f, 0));
    }

    void onEntityAttack(EntityAttackEvent e) {
    }

    void onPlayerDeath(PlayerDeathEvent e) {
    }

    void onPickupItem(PickupItemEvent e) {
    }

    void onItemDrop(ItemDropEvent e) {
    }

    void onPlayerDisconnect(PlayerDisconnectEvent e) {
        Player player = e.getPlayer();

        if (player == null) {
            return;
        }

        PlayerQuitEvent event = new PlayerQuitEvent(player);
        this.server.getEventManager().emit(event);
        this.server.getPlayerManager().removePlayer(player);
    }

    void onPlayerPacketOut(PlayerPacketOutEvent e) {
    }

    void onPlayerPacket(PlayerPacketEvent e) {
    }

    void onPlayerUseItemOnBlock(PlayerUseItemOnBlockEvent e) {
    }

    void onPlayerBlockPlace(PlayerBlockPlaceEvent e) {
    }

    void onPlayerBlockInteract(PlayerBlockInteractEvent e) {
    }

    void onServerListPing(ServerListPingEvent e) {
        ResponseData data = e.getResponseData();
        ServerQuery query = this.server.getConfig().query;

        data.setDescription(Component.text(query.motd.replace("&", "§")));
        data.setMaxPlayer(query.max_players);
        data.setPlayersHidden(query.hide_players);
        data.setVersion(query.version);

        for (String sample : query.playersSample) {
            data.addEntry(NamedAndIdentified.named(sample));
        }

        if (this.favicon != null) {
            data.setFavicon(this.favicon);
        }
    }

    public void register(EventNode<Event> node) {
        node.addListener(EntityAttackEvent.class, this::onEntityAttack);
        node.addListener(PlayerDeathEvent.class, this::onPlayerDeath);
        node.addListener(PickupItemEvent.class, this::onPickupItem);
        node.addListener(ItemDropEvent.class, this::onItemDrop);
        node.addListener(PlayerDisconnectEvent.class, this::onPlayerDisconnect);
        node.addListener(AsyncPlayerConfigurationEvent.class, this::onAsyncPlayerConfiguration);
        node.addListener(PlayerSpawnEvent.class, this::onPlayerSpawn);
        node.addListener(PlayerPacketOutEvent.class, this::onPlayerPacketOut);
        node.addListener(PlayerPacketEvent.class, this::onPlayerPacket);
        node.addListener(PlayerUseItemOnBlockEvent.class, this::onPlayerUseItemOnBlock);
        node.addListener(PlayerBlockPlaceEvent.class, this::onPlayerBlockPlace);
        node.addListener(PlayerBlockInteractEvent.class, this::onPlayerBlockInteract);
        node.addListener(ServerListPingEvent.class, this::onServerListPing);
    }
}
