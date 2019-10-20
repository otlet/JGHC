package com.idkcloud.JestemGraczem.Alcoholism;

import com.idkcloud.JestemGraczem.JGHC;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Random;

/**
 * Klasa odpowiadająza za nasłuchiwanie i wykonywanie efektów alkoholu
 * @since 1.0.1
 */
public class AlcoholismListener implements Listener {
    private JGHC main = JGHC.getPlugin(JGHC.class);

//    @EventHandler(ignoreCancelled = true)
//    public void onConsumeEvent(PlayerItemConsumeEvent event) {
//        main.getLogger().info("Początek eventu onConsumeEvent");
//        onItemConsumeEvent(event);
//    }

    /**
     * Metoda wywoływania, gdy gracz konsumuje przedmiot
     * @param event Instancja eventu przekazywana jako parametr
     * @since 1.0.1
     */
    @EventHandler(ignoreCancelled = true)
    public void onItemConsumeEvent(PlayerItemConsumeEvent event) {
        if (!event.getItem().hasItemMeta()) return;
        ItemMeta itemMeta = event.getItem().getItemMeta();
        if (!itemMeta.hasDisplayName()) return;

        Player player = event.getPlayer();

        generatePotionEffects(itemMeta.getDisplayName(), player);
    }

    /**
     * Metoda, która generuje efekty w potionach.
     * @param name Potion name
     * @param player Player instance
     * @since 1.0.1
     */
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
