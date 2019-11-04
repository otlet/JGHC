package com.idkcloud.JestemGraczem.Alcoholism;

import com.idkcloud.JestemGraczem.JGHC;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class AlcoholismConfig {
    private JGHC main = JGHC.getPlugin(JGHC.class);
    private File configFile;
    private FileConfiguration Config;

    public AlcoholismConfig() {
        createConfig();
    }

    public FileConfiguration getConfig() {
        return this.Config;
    }

    private void createConfig() {
        configFile = new File(main.getDataFolder(), "custom.yml");
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            main.saveResource("alcohols.yml", false);
        }

        Config = new YamlConfiguration();
        try {
            Config.load(configFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
}
