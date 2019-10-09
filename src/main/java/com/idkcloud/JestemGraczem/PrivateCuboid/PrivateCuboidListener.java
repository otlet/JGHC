//package com.idkcloud.JestemGraczem.PrivateCuboid;
//
//import com.idkcloud.JestemGraczem.JestemGraczem;
//import net.raidstone.wgevents.events.RegionEnteredEvent;
//import net.raidstone.wgevents.events.RegionsLeftEvent;
//import org.bukkit.entity.Player;
//import org.bukkit.event.EventHandler;
//import org.bukkit.event.Listener;
//
//public class PrivateCuboidListener implements Listener {
//    private JestemGraczem main = JestemGraczem.getPlugin(JestemGraczem.class);
//
//    @EventHandler
//    public void onRegionEntered(RegionEnteredEvent event) {
//        Player p = event.getPlayer();
//        if (p == null) {
//            return;
//        }
//
//        if (event.getRegion().getOwners().contains(event.getUUID()) && !main.perms.has(p, "essentials.fly")) {
//            main.perms.playerAdd(p, "essentials.fly");
//        }
//    }
//
//    @EventHandler
//    public void onRegionsLeft(RegionsLeftEvent event) {
//        Player p = event.getPlayer();
//        if (p == null) {
//            return;
//        }
//
//        p.setFlying(false);
//        if (main.perms.has(p, "essentials.fly"))
//            main.perms.playerRemove(p, "essentials.fly");
//    }
//}
