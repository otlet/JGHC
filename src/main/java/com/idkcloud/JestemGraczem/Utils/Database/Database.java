package com.idkcloud.JestemGraczem.Utils.Database;

import com.idkcloud.JestemGraczem.JGHC;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.logging.Level;

public abstract class Database {
    JGHC plugin;
    Connection connection;
    public int tokens = 0;

    Database(JGHC instance) {
        plugin = instance;
    }

    public abstract Connection getSQLConnection();

    public abstract void load();

    void initialize() {
        connection = getSQLConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM tchest_list");
            ResultSet rs = ps.executeQuery();
            close(ps, rs);
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, "Unable to retreive connection", ex);
        }
    }

    public Integer getTchestList(Player player) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs;
        try {
            conn = getSQLConnection();
            ps = conn.prepareStatement("SELECT * FROM tchest_items");
//            ps.setString(1, player.getName().toLowerCase());
//            ps.setInt(2, tokens);
//            ps.setInt(3, total);
            rs = ps.executeQuery();
            while (rs.next()) {
                player.sendMessage(String.format("%s %s", rs.getInt("id"), rs.getString("name")));
            }
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException ex) {
                plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionClose(), ex);
            }
        }
        return 0;
    }

    public boolean getCooldown(UUID uuid) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs;
        try {
            conn = getSQLConnection();
            ps = conn.prepareStatement("SELECT * FROM rtp_cooldown where uuid=?");
            ps.setString(1, uuid.toString().toLowerCase());
            rs = ps.executeQuery();
            int i = 0;
            while (rs.next()) {
                plugin.getLogger().info(String.format("%s %s", rs.getString("uuid"), rs.getString("last_time")));
                i++;
            }
            plugin.getLogger().info(String.valueOf(i));
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
            return false;
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException ex) {
                plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionClose(), ex);
            }
        }
        return true;
    }

    private void close(PreparedStatement ps, ResultSet rs) {
        try {
            if (ps != null)
                ps.close();
            if (rs != null)
                rs.close();
        } catch (SQLException ex) {
            Error.close(plugin, ex);
        }
    }
}
