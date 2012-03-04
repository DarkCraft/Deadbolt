package com.daemitus.deadbolt;

import java.util.Set;
import java.util.logging.Logger;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public final class Deadbolt {

    private static DeadboltPlugin plugin;
    private static Config config;

    public static void setPlugin(DeadboltPlugin plugin) {
        Deadbolt.plugin = plugin;
        Deadbolted.plugin = plugin;
    }

    public static void setConfig(Config config) {
        Deadbolt.config = config;
        config.load();
    }

    public static DeadboltPlugin getPlugin() {
        return plugin;
    }

    public static Logger getLogger() {
        return plugin.getLogger();
    }

    public static Config getConfig() {
        return config;
    }

    /**
     * Check if <player> or [Everyone] is on any of the [Private] or [More
     * Users] signs associated with <block>
     *
     * @param player Player to be checked
     * @param block Block to be checked
     * @return If <name> is authorized to use <block>
     */
    public static boolean isAuthorized(Player player, Block block) {
        return new Deadbolted(block).isUser(player);
    }

    /**
     * Check if <block> is protected by <player> or not
     *
     * @param player Player to be checked
     * @param block Block to be checked
     * @return If <player> owns <block>
     */
    public static boolean isOwner(Player player, Block block) {
        return new Deadbolted(block).isOwner(player);
    }

    /**
     * Retrieves all names authorized to interact with <block>
     *
     * @param block Block to be checked
     * @return A List<String> containing everything on any [Private] or [More
     * Users] signs associated with <block>
     */
    public static Set<String> getAllNames(Block block) {
        return new Deadbolted(block).getUsers();
    }

    /**
     * Retrieves owner of <block>
     *
     * @param block Block to be checked
     * @return The text on the line below [Private] on the sign associated with
     * <block>. null if unprotected
     */
    public static String getOwnerName(Block block) {
        return new Deadbolted(block).getOwner();
    }

    /**
     * Check if <block> is protected or not
     *
     * @param block The block to be checked
     * @return If <block> is owned
     */
    public static boolean isProtected(Block block) {
        return new Deadbolted(block).isProtected();
    }

    public static Deadbolted get(Block block) {
        return new Deadbolted(block);
    }
}
