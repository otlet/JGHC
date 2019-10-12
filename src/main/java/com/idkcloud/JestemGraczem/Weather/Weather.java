package com.idkcloud.JestemGraczem.Weather;

import com.idkcloud.JestemGraczem.JestemGraczem;
import org.bukkit.scheduler.BukkitScheduler;

public class Weather {
    public Weather() {
        JestemGraczem main = JestemGraczem.getPlugin(JestemGraczem.class);
        BukkitScheduler scheduler = main.getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(main, new WeatherRunnable(), 0, 100);
    }
}
