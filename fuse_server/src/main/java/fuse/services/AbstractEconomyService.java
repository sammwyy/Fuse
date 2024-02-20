package fuse.services;

import net.minestom.server.entity.Player;

public abstract class AbstractEconomyService implements Service {
    public abstract double getBalance(Player player);

    public abstract double getBalance(Player player, String id);

    public abstract void setBalance(Player player, double amount);

    public abstract double setBalance(Player player, double amount, String id);

    public abstract void deposit(Player player, double amount);

    public abstract void deposit(Player player, double amount, String id);

    public abstract void withdraw(Player player, double amount);

    public abstract void withdraw(Player player, double amount, String id);

    public abstract void createAccount(Player player);

    public abstract void createAccount(Player player, String id);

    public abstract boolean hasAccount(Player player);

    public abstract boolean hasAccount(Player player, String id);

    public abstract void deleteAccount(Player player);

    public abstract void deleteAccount(Player player, String id);

}
