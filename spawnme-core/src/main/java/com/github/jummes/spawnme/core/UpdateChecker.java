package com.github.jummes.spawnme.core;

import com.github.jummes.libs.util.MessageUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.scheduler.BukkitRunnable;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Credits to Benz56
 * https://www.spigotmc.org/threads/async-update-checker-for-premium-and-regular-plugins.327921/
 */
public class UpdateChecker {

    private final SpawnMe plugin;
    private final String localPluginVersion;
    private String spigotPluginVersion;

    private static final int ID = 75774;
    private static final String ERR_MSG = "&cUpdate checker failed!";
    private static final String UPDATE_MSG = "&eA new update of SpawnMe is available at:&6 https://www.spigotmc.org/resources/"
            + ID + "/updates";
    private static final Permission UPDATE_PERM = new Permission("spawnme.commands.*", PermissionDefault.FALSE);
    private static final long CHECK_INTERVAL = 12_000;

    public UpdateChecker() {
        this.plugin = SpawnMe.getInstance();
        this.localPluginVersion = plugin.getDescription().getVersion();
    }

    public void checkForUpdate() {
        new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                    try {
                        final HttpsURLConnection connection = (HttpsURLConnection) new URL(
                                "https://api.spigotmc.org/legacy/update.php?resource=" + ID).openConnection();
                        connection.setRequestMethod("GET");
                        spigotPluginVersion = new BufferedReader(new InputStreamReader(connection.getInputStream()))
                                .readLine();
                    } catch (final IOException e) {
                        Bukkit.getServer().getConsoleSender()
                                .sendMessage(MessageUtils.color(ERR_MSG));
                        e.printStackTrace();
                        cancel();
                        return;
                    }

                    if (localPluginVersion.equals(spigotPluginVersion))
                        return;

                    Bukkit.getServer().getConsoleSender()
                            .sendMessage(ChatColor.translateAlternateColorCodes('&', UPDATE_MSG));

                    Bukkit.getScheduler().runTask(plugin,
                            () -> Bukkit.getPluginManager().registerEvents(new Listener() {
                                @EventHandler(priority = EventPriority.MONITOR)
                                public void onPlayerJoin(final PlayerJoinEvent event) {
                                    final Player player = event.getPlayer();
                                    if (!player.hasPermission(UPDATE_PERM))
                                        return;
                                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', UPDATE_MSG));
                                }
                            }, plugin));

                    cancel();
                });
            }
        }.runTaskTimer(plugin, 0, CHECK_INTERVAL);
    }
}