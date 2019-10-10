package com.idkcloud.JestemGraczem.Alcoholism;

import com.idkcloud.JestemGraczem.JestemGraczem;
import com.idkcloud.JestemGraczem.Utils.Pair;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class AlcoholismListener implements Listener {
    private JestemGraczem main = JestemGraczem.getPlugin(JestemGraczem.class);

//    @EventHandler(ignoreCancelled = true)
//    public void onConsumeEvent(PlayerItemConsumeEvent event) {
//        main.getLogger().info("Początek eventu onConsumeEvent");
//        onItemConsumeEvent(event);
//    }

    @EventHandler(ignoreCancelled = true)
    public void onItemConsumeEvent(PlayerItemConsumeEvent event) {
        if (!event.getItem().hasItemMeta()) return;
        ItemMeta itemMeta = event.getItem().getItemMeta();
        if (!itemMeta.hasDisplayName()) return;

        Player player = event.getPlayer();

        generatePotionEffects(itemMeta.getDisplayName(), player);
    }

    private void generatePotionEffects(String name, Player player) {
        Random rand = new Random();
        int chance = rand.nextInt(100);
        AlcoholEffects alco = new AlcoholEffects(player);

        switch (name) {
            case "Amarena":
                alco.doAmarenaEffects();
                break;
            case "Wilnikufka":
                alco.doWilnikufkaEffects();
                break;
            case "Otletufka":
                alco.doOtletufkaEffects();
                break;
            case "Dziwna Butelka":
                alco.doWeirdBootleEffects(chance);
                break;
            case "Wódka":
                alco.doVodkaEffects();
                break;
            case "Piwo":
                alco.doBeerEffects();
                break;
            case "Bimber":
            case "Rum":
            case "Whiskey":
            case "Whisky":
                alco.doStandardAlcoholEffect();
                break;
        }
    }
}
