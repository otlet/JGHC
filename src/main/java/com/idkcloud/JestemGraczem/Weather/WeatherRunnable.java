package com.idkcloud.JestemGraczem.Weather;

import com.idkcloud.JestemGraczem.JGHC;
import org.bukkit.World;

public class WeatherRunnable implements Runnable {
    private JGHC main = JGHC.getPlugin(JGHC.class);
    private double nightTick = 14000;
    private World world;

    @Override
    public void run() {
        world = main.getServer().getWorld("world");
        try {
            if (world.getTime() > nightTick) {
                changeWeather();
            }
        } catch (NullPointerException m) {
            main.getLogger().warning(m.getMessage());
        }
    }

    private void changeWeather() {
        if (!world.hasStorm())
            world.setStorm(true);
        if (world.isThundering())
            world.setThundering(true);
    }
}
