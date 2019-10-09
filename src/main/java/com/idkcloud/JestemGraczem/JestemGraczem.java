package com.idkcloud.JestemGraczem;

import com.idkcloud.JestemGraczem.DeathSpawn.DeathSpawn;
import com.idkcloud.JestemGraczem.DeathSpawn.DeathSpawnListener;
import com.idkcloud.JestemGraczem.PrivateCuboid.PrivateCuboidListener;
import com.idkcloud.JestemGraczem.RandomTeleport.RandomTeleport;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class JestemGraczem extends JavaPlugin implements Listener {
    public Permission perms;

    @Override
    public void onEnable() {
        // Rejestracja pluginu
        getLogger().info("JestemGraczem.pl Wersja: " + getDescription().getVersion());
        getServer().getPluginManager().registerEvents(this, this);

        // Tryb debugowania, może kiedyś
        if (getConfig().getBoolean("debug")) {
            getLogger().info("DEBUG MODE");
        }

        // Włączenie dziwnych opcji
        if (!setupPermissions()) getServer().getPluginManager().disablePlugin(this);

        // Dodanie funkcjonalności z paczki DeathSpawn
        this.getCommand("setdeathspawn").setExecutor(new DeathSpawn());
        getServer().getPluginManager().registerEvents(new DeathSpawnListener(), this);

        // Dodanie Private Cuboid Ficzerów
        getServer().getPluginManager().registerEvents(new PrivateCuboidListener(), this);

        // Dodanie RandomTP
        this.getCommand("randomtp").setExecutor(new RandomTeleport());
    }

    @Override
    public void onDisable() {
        getLogger().info("Baj, baj maszkaro!");
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }

}
