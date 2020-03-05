package com.github.jummes.spawnme.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import com.github.jummes.spawnme.core.SpawnMe;
import com.github.jummes.spawnme.manager.SpawnManager;
import com.github.jummes.spawnme.spawn.Spawn;

public class PlayerRespawnListener implements Listener {

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent e) {
		SpawnManager spawnManager = SpawnMe.getInstance().getSpawnManager();

		String spawnId = SpawnMe.getInstance().getConfig().getString("deathSpawn");
		Spawn toSpawn = spawnId == "none" ? null : spawnManager.getSpawn(spawnId);
		if (toSpawn != null) {
			e.setRespawnLocation(toSpawn.getLocation().getWrapped());
		}
	}

}
