package com.github.jummes.spawnme.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.permissions.Permission;

import com.github.jummes.libs.command.AbstractCommand;
import com.github.jummes.libs.core.Libs;
import com.github.jummes.spawnme.core.SpawnMe;
import com.github.jummes.spawnme.manager.SpawnManager;
import com.github.jummes.spawnme.spawn.Spawn;

public class SpawnCommand extends AbstractCommand {

	public SpawnCommand(CommandSender sender, String subCommand, String[] arguments, boolean isSenderPlayer) {
		super(sender, subCommand, arguments, isSenderPlayer);
	}

	@Override
	protected void execute() {
		Player p = (Player) sender;
		SpawnManager spawnManager = SpawnMe.getInstance().getSpawnManager();

		// If there is an argument it's the spawn name, else it's the default spawn, if
		// not found returns null
		Spawn toTeleport = spawnManager.getSpawn(subCommand != "" ? subCommand : "default");

		if (toTeleport != null) {
			p.teleport(toTeleport.getLocation().getWrapped(), TeleportCause.PLUGIN);
			p.sendMessage(Libs.getLocale().get("messages.spawn.success"));
		} else {
			p.sendMessage(Libs.getLocale().get("messages.spawn.unknown-spawn"));
		}

	}

	@Override
	protected boolean isOnlyPlayer() {
		return true;
	}

	@Override
	protected Permission getPermission() {
		return new Permission("spawnme.spawn." + (arguments.length == 1 ? arguments[0] : "default"));
	}

}
