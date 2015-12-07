package com.daemitus.deadbolt.events;

import com.daemitus.deadbolt.*;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.DoubleChest;
import org.bukkit.block.Hopper;
import org.bukkit.entity.minecart.HopperMinecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class HopperMinecartListener implements Listener {

    private final DeadboltPlugin plugin = Deadbolt.getPlugin();

    public HopperMinecartListener() {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onInventoryMoveItemEvent(InventoryMoveItemEvent event) {
        Inventory initiator = event.getInitiator();

        if (initiator.getHolder() instanceof HopperMinecart) {
            Inventory other = event.getSource();
            if (other == initiator) {
                other = event.getDestination();
            }

            if (Deadbolt.isProtected(other)) {
                event.setCancelled(true);
            }
        } else if (initiator.getHolder() instanceof Hopper) {
            String hopperOwner = Deadbolt.getOwnerName(((BlockState) initiator.getHolder()).getBlock());
            String otherOwner = null;
            if (initiator == event.getSource()) {
                InventoryHolder dest = event.getDestination().getHolder();
                if (dest instanceof DoubleChest) {
                    dest = ((DoubleChest)dest).getLeftSide();
                }
                
                if (dest instanceof BlockState) {
                    Block b = ((BlockState) dest).getBlock();
                    otherOwner = Deadbolt.getOwnerName(b);
                }
            } else {
                InventoryHolder source = event.getSource().getHolder();
                if (source instanceof DoubleChest) {
                    source = ((DoubleChest)source).getLeftSide();
                }
                
                if (source instanceof BlockState) {
                    Block b = ((BlockState) source).getBlock();
                    otherOwner = Deadbolt.getOwnerName(b);
                }
            }
            
            if (otherOwner != null && (hopperOwner == null || !otherOwner.equalsIgnoreCase(hopperOwner))) {
                event.setCancelled(true);
            }
        }

        
    }
}
