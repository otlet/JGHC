package com.idkcloud.JestemGraczem.RandomTeleport;

import com.idkcloud.JestemGraczem.JestemGraczem;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class RandomTeleport implements CommandExecutor {
    private JestemGraczem main = JestemGraczem.getPlugin(JestemGraczem.class);
    private int cooldown;
    private HashMap<String, Long> cooldowns = new HashMap<>();

    public RandomTeleport() {
        this.generateDefaultConfiguration();
        cooldown = main.getConfig().getInt("RandomTeleport.Cooldown");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        // Sprawdzenie, czy RTP jest włączone w konfiguracji
        if (!isTeleportationActivated(sender)) {
            return false;
        }

        //Weryfikacje, czy wysyłający komendę jest gracz
        if (!(sender instanceof Player))
            return false;

        Player player = (Player) sender;

        //Czy gracz już jest w pamięci cooldowna
        if (!isCooldownPassed(player.getName(), sender)) {
            return false;
        }

        // Weryfikacja, czy gracz jest na odpowiedniej mapie
        if (!worldIsAllowedToRandomTeleport(player))
            return false;

        // Zapisywanie konfiguracji do osobnej zmiennej
        int minX = main.getConfig().getInt("RandomTeleport.Location.MinX");
        int minZ = main.getConfig().getInt("RandomTeleport.Location.MinZ");
        int maxX = main.getConfig().getInt("RandomTeleport.Location.MaxX");
        int maxZ = main.getConfig().getInt("RandomTeleport.Location.MaxZ");
        List<String> avoid = main.getConfig().getStringList("RandomTeleport.Avoid");

        // Generowanie randomowego miejsca
        Random r = new Random();
        int X = r.nextInt(maxX - minX) + minX;      // Szerokość
        int Y = 255;                                       // Wysokość
        int Z = r.nextInt(maxZ - minZ) + minZ;     // Długość

        main.getLogger().info(String.format("X: %s | Y: %s | Z: %s", X, Y, Z));

        // Wykonuj dopóki Y > 0 (czyli wysokość)
        do {
            Location location = new Location(player.getWorld(), X, Y, Z);
            // Jeśli blok nie posiada zakazanego typu (np.: woda, czy lava) to teleportuj go + zapis cooldown
            if (!avoid.contains(location.getBlock().getType().name())) {
                Location tpLocation = new Location(player.getWorld(), X, Y + 1, Z);
                cooldowns.put(player.getName(), System.currentTimeMillis() * 1000);
                player.teleport(tpLocation);
                return true;
            }
            Y--;
        } while (Y > 0);

        return false;
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

    private boolean isTeleportationActivated(CommandSender sender) {
        if (!main.getConfig().getBoolean("RandomTeleport.Activate")) {
            String message = "Random Teleport jest wyłączony!";
            sender.sendMessage(message);
            return false;
        }
        return true;
    }

    private boolean isCooldownPassed(String name, CommandSender sender) {
        if (cooldowns.containsKey(name)) {
            //Jeśli tak, to czy jego cooldown już minął.
            if (cooldowns.get(name) - System.currentTimeMillis() * 1000 > cooldown) {
                sender.sendMessage("Nie tak szybko!");
                return false;
            }
        }
        return true;
    }

    private boolean worldIsAllowedToRandomTeleport(Player player) {
        String world = main.getConfig().getString("RandomTeleport.Location.World");
        if (!player.getWorld().getName().equals(world)) {
            player.sendMessage("Tylko można na mapie: " + world);
            return false;
        }
        return true;
    }
}
