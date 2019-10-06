package com.idkcloud.JestemGraczem.DeathSpawn;

import com.idkcloud.JestemGraczem.JestemGraczem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

public class DeathSpawnListener implements Listener {
    private JestemGraczem main = JestemGraczem.getPlugin(JestemGraczem.class);

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (main.getConfig().getBoolean("debug")) {
            player.sendMessage("You are awesome!");
        } else {
            player.sendMessage("You are not awesome...");
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerRespawn(final PlayerRespawnEvent e) {
        if (main.getConfig().getBoolean("debug")) {
            main.getLogger().info("Umar: " + e.getPlayer().getName());
            main.getLogger().info(e.getPlayer().getLocation().toString());
        }

        Bukkit.getScheduler().runTaskLater(main, new Runnable() {
            @Override
            public void run() {
                if (main.getConfig().getBoolean("DeathJail.Activated")) {
                    Player player = e.getPlayer();
                    player.getInventory().setHelmet(new ItemStack(Material.LEATHER_HELMET, 1));
                    player.getInventory().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE, 1));
                    player.getInventory().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS, 1));
                    player.getInventory().setBoots(new ItemStack(Material.LEATHER_BOOTS, 1));
                    player.sendMessage(ChatColor.DARK_RED + "Witamy w piekle, cwaniaku :)");

                    double x, y, z;
                    String world = main.getConfig().getString("DeathJail.Location.World");
                    x = main.getConfig().getDouble("DeathJail.Location.X");
                    y = main.getConfig().getDouble("DeathJail.Location.Y");
                    z = main.getConfig().getDouble("DeathJail.Location.Z");
                    Location loc = new Location(Bukkit.getWorld(world != null ? world : "world"), x, y, z);

                    main.getLogger().info(loc.toString());
                    e.getPlayer().teleport(loc);
                }
            }
        }, 5);
    }
}
