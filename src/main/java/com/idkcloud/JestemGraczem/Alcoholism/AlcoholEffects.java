package com.idkcloud.JestemGraczem.Alcoholism;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

class AlcoholEffects {
    private Player player;

    AlcoholEffects(Player p) {
        player = p;
    }

    void doAmarenaEffects() {
        PotionEffect blindness = new PotionEffect(
                PotionEffectType.BLINDNESS,
                36000,
                8
        );
        player.addPotionEffect(blindness);
    }

    void doWilnikufkaEffects() {
        PotionEffect luck = new PotionEffect(
                PotionEffectType.LUCK,
                36000,
                8
        );
        player.addPotionEffect(luck);
    }

    void doOtletufkaEffects() {
        PotionEffect unluck = new PotionEffect(
                PotionEffectType.UNLUCK,
                36000,
                8
        );
        player.addPotionEffect(unluck);
    }

    void doWeirdBootleEffects(int chance) {
        PotionEffectType effectType;
        int duration = 3600;
        if (chance > 35 && chance < 65) {
            effectType = PotionEffectType.LUCK;
        } else if (chance >= 65) {
            effectType = PotionEffectType.UNLUCK;
        } else {
            effectType = PotionEffectType.HEAL;
            duration = 10;
        }
        PotionEffect bootleEffect = new PotionEffect(
                effectType,
                duration,
                8
        );
        player.addPotionEffect(bootleEffect);
    }

    public void doVodkaEffects() {
        PotionEffect confusion = new PotionEffect(
                PotionEffectType.CONFUSION,
                1600,
                8
        );
        player.addPotionEffect(confusion);
    }

    void doBeerEffects() {
        PotionEffect confusion = new PotionEffect(
                PotionEffectType.CONFUSION,
                360,
                8
        );
        player.addPotionEffect(confusion);
    }

    void doStandardAlcoholEffect() {
        PotionEffect confusion = new PotionEffect(
                PotionEffectType.CONFUSION,
                3600,
                8
        );
        player.addPotionEffect(confusion);
    }
}
