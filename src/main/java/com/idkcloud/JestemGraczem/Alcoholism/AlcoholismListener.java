package com.idkcloud.JestemGraczem.Alcoholism;

import com.idkcloud.JestemGraczem.JestemGraczem;
import com.idkcloud.JestemGraczem.Utils.Pair;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class AlcoholismListener implements Listener {
    private JestemGraczem main = JestemGraczem.getPlugin(JestemGraczem.class);

//    @EventHandler(ignoreCancelled = true)
//    public void onConsumeEvent(PlayerItemConsumeEvent event) {
//        main.getLogger().info("Początek eventu onConsumeEvent");
//        onItemConsumeEvent(event);
//    }
//
//    @EventHandler(ignoreCancelled = true)
//    public void onConsume(PlayerItemConsumeEvent event) {
//        main.getLogger().info("Początek eventu onConsume");
//        onItemConsumeEvent(event);
//    }
//
//    @EventHandler(ignoreCancelled = true)
//    public void onPlayerItemConsumeEvent(PlayerItemConsumeEvent event) {
//        main.getLogger().info("Początek eventu onPlayerItemConsumeEvent");
//        onItemConsumeEvent(event);
//    }

    @EventHandler(ignoreCancelled = true)
    public void onItemConsumeEvent(PlayerItemConsumeEvent event) {
        ItemStack item = event.getItem();
        if (!item.getItemMeta().hasDisplayName()) return;

        Player player = event.getPlayer();

        Pair<Boolean, Pair<PotionEffectType, Integer>> potionMagic = getPotion(item.getItemMeta().getDisplayName());

        if (potionMagic.getKey()) {
            PotionEffect potionEffect = new PotionEffect(
                    potionMagic.getValue().getKey(),
                    potionMagic.getValue().getValue(),
                    8
            );
            player.addPotionEffect(potionEffect);
        }
    }

    private Pair<Boolean, Pair<PotionEffectType, Integer>> getPotion(String name) {
        boolean isRegisteredPotion = false;
        PotionEffectType effectType = PotionEffectType.HEAL;
        int duration = 0;

        Random rand = new Random();
        int chance = rand.nextInt(100);

        switch (name) {
            case "Amarena":
                isRegisteredPotion = true;
                effectType = PotionEffectType.BLINDNESS;
                duration = 36000;
                break;
            case "Wilnikufka":
                isRegisteredPotion = true;
                effectType = PotionEffectType.LUCK;
                duration = 360000;
                break;
            case "Otletufka":
                isRegisteredPotion = true;
                effectType = PotionEffectType.UNLUCK;
                duration = 360000;
                break;
            case "Dziwna Butelka":
                isRegisteredPotion = true;
                duration = 3600;
                if (chance > 35 && chance < 65) {
                    effectType = PotionEffectType.LUCK;
                } else if (chance >= 65) {
                    effectType = PotionEffectType.UNLUCK;
                } else {
                    duration = 10;
                }
                break;
            case "Wódka":
                isRegisteredPotion = true;
                duration = 1600;
                effectType = PotionEffectType.CONFUSION;
                break;
            case "Piwo":
                isRegisteredPotion = true;
                duration = 360;
                effectType = PotionEffectType.CONFUSION;
                break;
            case "Bimber":
            case "Rum":
            case "Whiskey":
            case "Whisky":
                isRegisteredPotion = true;
                duration = 3600;
                effectType = PotionEffectType.CONFUSION;
                break;
        }

        Pair<PotionEffectType, Integer> datas = new Pair<>(effectType, duration);
        return new Pair<>(isRegisteredPotion, datas);
    }
}
