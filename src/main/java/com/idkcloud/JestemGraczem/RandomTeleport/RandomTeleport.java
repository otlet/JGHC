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
        this.generateDefaultConfiguration();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!main.getConfig().getBoolean("RandomTeleport.Activate")) {
            String message = "Random Teleport jest wyłączony!";
            sender.sendMessage(message);
            return true;
        }
        if (sender instanceof Player) {
            Player player = (Player) sender;
            String world = main.getConfig().getString("RandomTeleport.Location.World");
            if (player.getWorld().getName().equals(world)) {

                int minX = main.getConfig().getInt("RandomTeleport.Location.MinX");
                int minZ = main.getConfig().getInt("RandomTeleport.Location.MinZ");
                int maxX = main.getConfig().getInt("RandomTeleport.Location.MaxX");
                int maxZ = main.getConfig().getInt("RandomTeleport.Location.MaxZ");
                List<String> avoid = main.getConfig().getStringList("RandomTeleport.Avoid");

                Random r = new Random();
                int X = r.nextInt(maxX - minX) + minX;
                int Y = 255;
                int Z = r.nextInt(maxZ - minZ) + minZ;

                main.getLogger().info(String.format("X: %s | Y: %s | Z: %s", X, Y, Z));

                do {
                    Location location = new Location(player.getWorld(), X, Y, Z);
                    if (!avoid.contains(location.getBlock().getType().name()))
                        break;
                    Y--;
                } while (Y > 0);

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

    private void generateDefaultConfiguration() {
        main.getConfig().addDefault("RandomTeleport.Activate", false);
        main.getConfig().addDefault("RandomTeleport.Location.World", "world");
        main.getConfig().addDefault("RandomTeleport.Location.MinX", 0);
        main.getConfig().addDefault("RandomTeleport.Location.MinZ", 0);
        main.getConfig().addDefault("RandomTeleport.Location.MaxX", 0);
        main.getConfig().addDefault("RandomTeleport.Location.MaxZ", 0);
        main.getConfig().addDefault("RandomTeleport.Cooldown", 600);
        main.getConfig().addDefault("RandomTeleport.Avoid", getDefaultAvoidMaterial());
        main.getConfig().options().copyDefaults(true);
        main.saveConfig();
    }
}
