package com.idkcloud.JestemGraczem.Utils.Database;

import com.idkcloud.JestemGraczem.JGHC;
import com.idkcloud.JestemGraczem.RandomTeleport.RtpCooldownQuery;
import com.idkcloud.JestemGraczem.TreasureChest.QueryList;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

public class SQLite extends Database {
    private String dbname;
    private QueryList chests;
    private RtpCooldownQuery rtp_querys;

    public SQLite(JGHC instance) {
        super(instance);
        dbname = plugin.getConfig().getString("SQLite.Filename", "database");
        chests = new QueryList();
        rtp_querys = new RtpCooldownQuery();
    }

    // SQL creation stuff, You can leave the blow stuff untouched.
    public Connection getSQLConnection() {
        File dataFolder = new File(plugin.getDataFolder(), dbname + ".sqlite");
        if (!dataFolder.exists()) {
            try {
                dataFolder.createNewFile();
                plugin.getLogger().warning("Creating new SQLite database");
            } catch (IOException e) {
                plugin.getLogger().log(Level.SEVERE, "File write error: " + dbname + ".sqlite");
            }
        }
        try {
            if (connection != null && !connection.isClosed()) {
                return connection;
            }
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + dataFolder);
            return connection;
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, "SQLite exception on initialize", ex);
        } catch (ClassNotFoundException ex) {
            plugin.getLogger().log(Level.SEVERE, "You need the SQLite JBDC library. Google it. Put it in /lib folder.");
        }
        return null;
    }

    public void load() {
        connection = getSQLConnection();
        try {
            Statement s = connection.createStatement();
            s.executeUpdate(chests.CreateTreasureChestListTable);
            s.executeUpdate(chests.CreateTreasureChestItemTable);
            s.executeUpdate(chests.CreateTreasureChestCooldownTable);
            s.executeUpdate(rtp_querys.CreateRtpCooldownTable);
            s.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        initialize();
    }
}