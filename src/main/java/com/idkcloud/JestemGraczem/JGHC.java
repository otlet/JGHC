package com.idkcloud.JestemGraczem;

import com.idkcloud.JestemGraczem.Alcoholism.AlcoholismConfig;
import com.idkcloud.JestemGraczem.Alcoholism.AlcoholismListener;
import com.idkcloud.JestemGraczem.DeathSpawn.DeathSpawn;
import com.idkcloud.JestemGraczem.DeathSpawn.DeathSpawnListener;
import com.idkcloud.JestemGraczem.Notify.Notify;
import com.idkcloud.JestemGraczem.Notify.NotifyScheduler;
import com.idkcloud.JestemGraczem.RandomTeleport.RandomTeleport;
import com.idkcloud.JestemGraczem.Utils.Database.Database;
import com.idkcloud.JestemGraczem.Utils.Database.SQLite;
import com.idkcloud.JestemGraczem.Weather.Weather;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class JGHC extends JavaPlugin implements Listener {
    public AlcoholismConfig alcoholismConfig;
    private Database db;

    @Override
    public void onEnable() {
        // Rejestracja pluginu
        getServer().getPluginManager().registerEvents(this, this);

        // Database <3
        this.db = new SQLite(this);
        this.db.load();

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
        Notify notify = new Notify();
        notify.runScheduler();
    }

    @Override
    public void onDisable() {
        getLogger().info("Baj, baj maszkaro!");
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        Permission perms = rsp.getProvider();
        return perms != null;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        sendWelcomeMessage(event.getPlayer());
    }

    private void sendWelcomeMessage(Player player) {
        String title = "JestemGraczem.pl";
        String subtitle = "Najtrudniejszy serwer Hardcore w Polsce! Powodzenia!";
        int fadeIn = 10;
        int fadeOut = 20;
        int stay = 70;
        player.sendTitle(ChatColor.RED + title, ChatColor.WHITE + subtitle, fadeIn, stay, fadeOut);
    }

    public Database getRDatabase() {
        return this.db;
    }

}
