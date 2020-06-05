package com.github.jummes.spawnme.listener;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

import com.github.jummes.spawnme.core.SpawnMe;
import com.github.jummes.spawnme.manager.SpawnManager;
import com.github.jummes.spawnme.spawn.Spawn;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerSpawnLocationEvent e) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(e.getPlayer().getUniqueId());
        SpawnManager spawnManager = SpawnMe.getInstance().getSpawnManager();

        String spawnId;
        if (!offlinePlayer.hasPlayedBefore()) {
            spawnId = SpawnMe.getInstance().getConfig().getString("firstJoinSpawn");
        } else {
            spawnId = SpawnMe.getInstance().getConfig().getString("joinSpawn");
        }

        Spawn spawn = spawnId == "none" ? null : spawnManager.getSpawn(spawnId);
        if (spawn != null) {
            e.setSpawnLocation(spawn.getLocation().getWrapped());
        }
    }

}
