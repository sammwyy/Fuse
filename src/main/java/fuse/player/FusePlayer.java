package fuse.player;

import org.jetbrains.annotations.NotNull;

import net.kyori.adventure.audience.MessageType;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.sound.Sound.Emitter;
import net.kyori.adventure.sound.SoundStop;
import net.kyori.adventure.text.Component;
import net.minestom.server.coordinate.Point;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.Chunk;

public class FusePlayer {
    private Player player;

    public FusePlayer(Player player) {
        this.player = player;
    }

    public boolean isOnGround() {
        return this.player.isOnGround();
    }

    public void kill() {
        this.player.kill();
    }

    public String getName() {
        return this.player.getUsername();
    }

    public void playSound(@NotNull Sound sound) {
        this.player.playSound(sound);
    }

    public void playSound(@NotNull Sound sound, @NotNull Point point) {
        this.player.playSound(sound, point);
    }

    public void playSound(@NotNull Sound sound, double x, double y, double z) {
        this.player.playSound(sound, x, y, z);
    }

    public void playSound(@NotNull Sound sound, @NotNull Emitter emitter) {
        this.player.playSound(sound, emitter);
    }

    public void remove(boolean permanent) {
        this.player.remove(permanent);
    }

    public void respawn() {
        this.player.respawn();
    }

    public void sendChunk(@NotNull Chunk chunk) {
        this.player.sendChunk(chunk);
    }

    @SuppressWarnings("deprecation")
    public void sendMessage(@NotNull Identity source, @NotNull Component message, @NotNull MessageType type) {
        this.player.sendMessage(source, message);
    }

    @SuppressWarnings("deprecation")
    public void sendMessage(@NotNull Identity source, @NotNull Component message) {
        this.sendMessage(source, message, MessageType.SYSTEM);
    }

    public void sendMessage(@NotNull Component message, @NotNull MessageType type) {
        this.sendMessage(Identity.nil(), message, type);
    }

    public void sendMessage(@NotNull Component message) {
        this.sendMessage(Identity.nil(), message, MessageType.SYSTEM);
    }

    public void sendMessage(@NotNull String message) {
        this.sendMessage(Component.text(message));
    }

    public void sendPluginMessage(@NotNull String channel, byte @NotNull [] data) {
        this.player.sendPluginMessage(channel, data);
    }

    public void sendPluginMessage(@NotNull String channel, String message) {
        this.player.sendPluginMessage(channel, message);
    }

    public void stopSound(@NotNull SoundStop stop) {
        this.player.stopSound(stop);
    }

    public void stopSounds() {
        this.player.stopSound(SoundStop.all());
    }

    public void dispose() {
    }
}
