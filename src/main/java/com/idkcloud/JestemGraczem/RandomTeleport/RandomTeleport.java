package com.idkcloud.JestemGraczem.RandomTeleport;

import com.idkcloud.JestemGraczem.JestemGraczem;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;

public class RandomTeleport implements CommandExecutor {
    private JestemGraczem main = JestemGraczem.getPlugin(JestemGraczem.class);

    public RandomTeleport() {
        main.getConfig().addDefault("RandomTeleport.Activated", false);
        main.getConfig().addDefault("RandomTeleport.Location.World", "world");
        main.getConfig().addDefault("RandomTeleport.Location.MinX", 0);
        main.getConfig().addDefault("RandomTeleport.Location.MinY", 0);
        main.getConfig().addDefault("RandomTeleport.Location.MaxX", 0);
        main.getConfig().addDefault("RandomTeleport.Location.MaxY", 0);
        main.getConfig().addDefault("RandomTeleport.Cooldown", 600);
        main.getConfig().addDefault("RandomTeleport.Avoid", getDefaultAvoidMaterial());
        main.getConfig().options().copyDefaults(true);
        main.saveConfig();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            String world = main.getConfig().getString("RandomTeleport.Location.World");
            if (player.getWorld().getName().equals(world)) {

                int minX = main.getConfig().getInt("RandomTeleport.Location.MinX");
                int minY = main.getConfig().getInt("RandomTeleport.Location.MinY");
                int maxX = main.getConfig().getInt("RandomTeleport.Location.MaxX");
                int maxY = main.getConfig().getInt("RandomTeleport.Location.MaxY");
                List<String> avoid = main.getConfig().getStringList("RandomTeleport.Avoid");

                Random r = new Random();
                int X = r.nextInt(maxX - minX) + minX;
                int Y = r.nextInt(maxY - minY) + minY;
                int Z = 255;

                while (Z > 0) {
                    Location location = new Location(player.getWorld(), X, Y, Z);
                    if (!avoid.contains(location.getBlock().getType().name()))
                        break;
                    Z--;
                }

                Location location = new Location(player.getWorld(), X, Y, Z);
                player.teleport(location);

            } else {
                player.sendMessage("Działa tylko na mapie: " + world);
            }
        } else {
            sender.sendMessage("Komenda tylko do wykonania jako gracz!");
        }
        return true;
    }

    private String[] getDefaultAvoidMaterial() {
        String[] avoid = new String[3];
        avoid[0] = "water";
        avoid[1] = "lava";
        avoid[2] = "air";
        return avoid;
    }
}
