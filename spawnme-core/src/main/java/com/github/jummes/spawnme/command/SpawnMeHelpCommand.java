package com.github.jummes.spawnme.command;

import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;

import com.github.jummes.libs.command.AbstractCommand;
import com.github.jummes.libs.util.MessageUtils;

public class SpawnMeHelpCommand extends AbstractCommand {

	private final static String HELP_MSG = MessageUtils.header("SpawnMe Help")
			+ MessageUtils.color(
					"&2/spawnme help &7Print the help message.\n" + "&2/spawnme reload &7Reload configuration files.\n"
							+ "&2/spawnme menu &7Open the menu configuration GUI\n"
							+ "&2/spawnme spawns &7Open the spawn configuration GUI.\n"
							+ "&2/spawn [id] &7Teleport to the spawn [id], if no [id] is provided 'default' is used as id.\n"
							+ "&2/setspawn [id] &7Set the spawn [id] to the player location, if no [id] is provided 'default' is used as id.\n"+
							"&2/spawns &7Open the spawns menu.\n")
			+ MessageUtils.delimiter();

	public SpawnMeHelpCommand(CommandSender sender, String subCommand, String[] arguments, boolean isSenderPlayer) {
		super(sender, subCommand, arguments, isSenderPlayer);
	}

	@Override
	protected void execute() {
		sender.sendMessage(HELP_MSG);
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
