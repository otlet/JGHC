package com.idkcloud.JestemGraczem.Weather;

import com.idkcloud.JestemGraczem.JGHC;
import org.bukkit.scheduler.BukkitScheduler;

public class Weather {
    public Weather() {
        JGHC main = JGHC.getPlugin(JGHC.class);
        BukkitScheduler scheduler = main.getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(main, new WeatherRunnable(), 0, 100);
    }
}
