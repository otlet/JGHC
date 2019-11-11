package com.idkcloud.JestemGraczem.Notify;

import com.idkcloud.JestemGraczem.JGHC;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.bukkit.scheduler.BukkitRunnable;

public class NotifyScheduler extends BukkitRunnable {
    private JGHC main;
    String apiUrl;
    String serverType;

    NotifyScheduler(JGHC mainClass) {
        main = mainClass;
        apiUrl = main.getConfig().getString("API.NotifyURL");
        serverType = main.getConfig().getString("API.NotifyServerType");
    }

    private HttpResponse<JsonNode> runQuery() {
        return Unirest.post(apiUrl)
                .header("accept", "application/json")
                .queryString("X-Minecraft-Server", serverType)
                .asJson();
    }

    @Override
    public void run() {
        HttpResponse<JsonNode> response = this.runQuery();
        main.getLogger().info(response.getStatusText());
    }
}