package com.idkcloud.JestemGraczem.Notify;

import com.idkcloud.JestemGraczem.JGHC;

public class Notify {
    private JGHC main = JGHC.getPlugin(JGHC.class);

    public Notify() {
        main.getConfig().addDefault("API.NotifyURL", "https://jestemgraczem.pl/api/minecraft/notify/");
        main.getConfig().addDefault("API.NotifyServerType", "hardcore");
    }

    public void runScheduler() {
        NotifyScheduler scheduler = new NotifyScheduler(main);
        scheduler.runTaskLater(main, 10);
    }
}
