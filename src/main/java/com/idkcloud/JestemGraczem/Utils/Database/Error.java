package com.idkcloud.JestemGraczem.Utils.Database;

import com.idkcloud.JestemGraczem.JGHC;

import java.util.logging.Level;

public class Error {
    public static void execute(JGHC plugin, Exception ex) {
        plugin.getLogger().log(Level.SEVERE, "Couldn't execute MySQL statement: ", ex);
    }

    public static void close(JGHC plugin, Exception ex) {
        plugin.getLogger().log(Level.SEVERE, "Failed to close MySQL connection: ", ex);
    }
}