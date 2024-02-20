package fuse.services;

import net.minestom.server.command.CommandSender;

public abstract class AbstractPermissionService implements Service {
    public abstract boolean hasPermission(CommandSender sender, String permission);

    public abstract void registerPermission(String permission);

    public abstract void unregisterPermission(String permission);
}
