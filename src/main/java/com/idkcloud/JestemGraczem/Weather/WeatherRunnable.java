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
        if (world.getTime() > nightTick) {
            changeWeather();
        }
    }

    private void changeWeather() {
        if (!world.hasStorm())
            world.setStorm(true);
        if (world.isThundering())
            world.setThundering(true);
    }
}
