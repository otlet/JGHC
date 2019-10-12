package com.idkcloud.JestemGraczem;

import com.idkcloud.JestemGraczem.Alcoholism.AlcoholismConfig;
import com.idkcloud.JestemGraczem.Alcoholism.AlcoholismListener;
import com.idkcloud.JestemGraczem.DeathSpawn.DeathSpawn;
import com.idkcloud.JestemGraczem.DeathSpawn.DeathSpawnListener;
import com.idkcloud.JestemGraczem.RandomTeleport.RandomTeleport;
import com.idkcloud.JestemGraczem.Weather.Weather;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

//import com.idkcloud.JestemGraczem.PrivateCuboid.PrivateCuboidListener;

public class JestemGraczem extends JavaPlugin implements Listener {
    public Permission perms;
    public AlcoholismConfig alcoholismConfig;

    @Override
    public void onEnable() {
        // Rejestracja pluginu
        getServer().getPluginManager().registerEvents(this, this);

        // Tryb debugowania, może kiedyś
        if (getConfig().getBoolean("debug")) {
            getLogger().info("DEBUG MODE");
        }

        // Weryfikacja uprawnień
        if (!setupPermissions()) getServer().getPluginManager().disablePlugin(this);

        // Dodanie funkcjonalności z paczki DeathSpawn
        this.getCommand("setdeathspawn").setExecutor(new DeathSpawn());
        getServer().getPluginManager().registerEvents(new DeathSpawnListener(), this);

        // Dodanie alkoholu
//        alcoholismConfig = new AlcoholismConfig();
        getServer().getPluginManager().registerEvents(new AlcoholismListener(), this);

        // Dodanie RandomTP
        this.getCommand("randomtp").setExecutor(new RandomTeleport());

        // Dodanie taska z pogodą
        new Weather();

        // Dodanie Private Cuboid Ficzerów
//        getServer().getPluginManager().registerEvents(new PrivateCuboidListener(), this);
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
