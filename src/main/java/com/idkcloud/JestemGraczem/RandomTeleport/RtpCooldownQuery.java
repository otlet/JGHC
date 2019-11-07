package com.idkcloud.JestemGraczem.RandomTeleport;

public class RtpCooldownQuery {
    public String CreateRtpCooldownTable = "CREATE TABLE IF NOT EXISTS rtp_cooldown (" +
            "`uuid` varchar(36) NOT NULL," +
            "`last_time` INTEGER NOT NULL," +
            "PRIMARY KEY (`uuid`));";
}
