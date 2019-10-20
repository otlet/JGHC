package com.idkcloud.JestemGraczem.DeathSpawn;

import com.idkcloud.JestemGraczem.JGHC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DeathSpawn implements CommandExecutor {
    private JGHC main = JGHC.getPlugin(JGHC.class);

    public DeathSpawn() {
        main.getConfig().addDefault("debug", false);
        main.getConfig().addDefault("DeathJail.Activated", false);
        main.getConfig().addDefault("DeathJail.Location.World", "world");
        main.getConfig().addDefault("DeathJail.Location.X", 0);
        main.getConfig().addDefault("DeathJail.Location.Y", 0);
        main.getConfig().addDefault("DeathJail.Location.Z", 0);
        main.getConfig().options().copyDefaults(true);
        main.saveConfig();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            main.getConfig().set("DeathJail.Activated", true);
            main.getConfig().set("DeathJail.Location.World", player.getLocation().getWorld().getName());
            main.getConfig().set("DeathJail.Location.X", player.getLocation().getBlockX());
            main.getConfig().set("DeathJail.Location.Y", player.getLocation().getBlockY());
            main.getConfig().set("DeathJail.Location.Z", player.getLocation().getBlockZ());
            main.saveConfig();

            sender.sendMessage("Ustawiłeś DeathSpawn w lokalizacji: " + main.getConfig().getString("DeathJail.Location.World"));
        } else {
            sender.sendMessage("Komenda tylko do wykonania jako gracz!");
        }
        return true;
    }
}
