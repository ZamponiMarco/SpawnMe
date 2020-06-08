package com.github.jummes.spawnme.listener;

import com.github.jummes.spawnme.core.SpawnMe;
import com.github.jummes.spawnme.manager.SpawnManager;
import com.github.jummes.spawnme.spawn.Spawn;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.Comparator;

public class PlayerRespawnListener implements Listener {

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        SpawnManager spawnManager = SpawnMe.getInstance().getSpawnManager();

        String spawnId = SpawnMe.getInstance().getConfig().getString("deathSpawn");

        if (spawnId.equalsIgnoreCase("closest")) {
            spawnManager.getSpawns().stream().filter(spawn -> e.getPlayer().hasPermission("spawnme.spawn." + spawn.getId())).min(Comparator.comparingDouble(s -> s.getLocation().getWrapped().distance(e.getPlayer().getLocation()))).ifPresent(spawn -> e.setRespawnLocation(spawn.getLocation().getWrapped()));
        } else if (!spawnId.equalsIgnoreCase("none")) {
            Spawn toSpawn = spawnManager.getSpawn(spawnId);
            if (toSpawn != null) {
                e.setRespawnLocation(toSpawn.getLocation().getWrapped());
            }
        }
    }

}
