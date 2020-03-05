package com.github.jummes.spawnme.command;

import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;

import com.github.jummes.libs.command.AbstractCommand;
import com.github.jummes.spawnme.core.SpawnMe;

public class SpawnMeReloadCommand extends AbstractCommand {

	public SpawnMeReloadCommand(CommandSender sender, String subCommand, String[] arguments, boolean isSenderPlayer) {
		super(sender, subCommand, arguments, isSenderPlayer);
	}

	@Override
	protected void execute() {
		SpawnMe.getInstance().saveConfig();
		SpawnMe.getInstance().reloadConfig();
	}

	@Override
	protected boolean isOnlyPlayer() {
		return false;
	}

	@Override
	protected Permission getPermission() {
		return new Permission("spawnme.admin.reload");
	}

}
