package com.github.jummes.spawnme.command;

import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;

import com.github.jummes.libs.command.AbstractCommand;

public class SpawnMeHelpCommand extends AbstractCommand {

	public SpawnMeHelpCommand(CommandSender sender, String subCommand, String[] arguments, boolean isSenderPlayer) {
		super(sender, subCommand, arguments, isSenderPlayer);
	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub
	}

	@Override
	protected boolean isOnlyPlayer() {
		return false;
	}

	@Override
	protected Permission getPermission() {
		return new Permission("spawnme.admin.help");
	}

}
