package com.idkcloud.JestemGraczem.TreasureChest;

public class QueryList {
    public String CreateTreasureChestListTable = "CREATE TABLE IF NOT EXISTS tchest_list (" +
            "`id` integer NOT NULL," +
            "`locationX` integer NOT NULL," +
            "`locationY` integer NOT NULL," +
            "`locationZ` integer NOT NULL," +
            "PRIMARY KEY (`id`));";
    public String CreateTreasureChestCooldownTable = "CREATE TABLE IF NOT EXISTS tchest_cooldown (" +
            "`uuid` varchar(36) NOT NULL," +
            "`last_time` INTEGER NOT NULL," +
            "PRIMARY KEY (`uuid`));";
    public String CreateTreasureChestItemTable = "CREATE TABLE IF NOT EXISTS tchest_items (" +
            "`id` integer NOT NULL," +
            "`name` var_char(16) NOT NULL," +
            "`count` INTEGER NOT NULL," +
            "`chance` INTEGER NOT NULL," +
            "PRIMARY KEY (`id`));";
}
