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
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.Objects;
import java.util.Set;

public class DeathSpawnListener implements Listener {
    private JestemGraczem main = JestemGraczem.getPlugin(JestemGraczem.class);

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerRespawn(final PlayerRespawnEvent e) {
        if (main.getConfig().getBoolean("debug")) {
            main.getLogger().info("Umar: " + e.getPlayer().getName());
            main.getLogger().info(e.getPlayer().getLocation().toString());
        }

        Player player = e.getPlayer();

        Scoreboard scoreboard = Objects.requireNonNull(main.getServer().getScoreboardManager()).getMainScoreboard();
        Score playerScore = Objects.requireNonNull(scoreboard.getObjective("ZGONY")).getScore(player.getName());

        if (playerScore.getScore() > 500) {
            Bukkit.getScheduler().runTaskLater(main, () -> {
                if (main.getConfig().getBoolean("DeathJail.Activated")) {
                    player.getInventory().setHelmet(new ItemStack(Material.LEATHER_HELMET, 1));
                    player.getInventory().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE, 1));
                    player.getInventory().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS, 1));
                    player.getInventory().setBoots(new ItemStack(Material.LEATHER_BOOTS, 1));
                    player.sendMessage(ChatColor.DARK_RED + "Witamy w piekle w nagrodę za ponad 500 zgonów!");

                    double x, y, z;
                    String world = main.getConfig().getString("DeathJail.Location.World");
                    x = main.getConfig().getDouble("DeathJail.Location.X");
                    y = main.getConfig().getDouble("DeathJail.Location.Y");
                    z = main.getConfig().getDouble("DeathJail.Location.Z");
                    Location loc = new Location(Bukkit.getWorld(world != null ? world : "world"), x, y, z);

                    main.getLogger().info(loc.toString());
                    e.getPlayer().teleport(loc);
                }
            }, 5);
        }
    }
}
