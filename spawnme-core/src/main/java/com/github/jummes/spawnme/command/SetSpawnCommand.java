package com.github.jummes.spawnme.command;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import com.github.jummes.libs.command.AbstractCommand;
import com.github.jummes.libs.core.Libs;
import com.github.jummes.libs.model.wrapper.LocationWrapper;
import com.github.jummes.spawnme.core.SpawnMe;
import com.github.jummes.spawnme.manager.SpawnManager;

public class SetSpawnCommand extends AbstractCommand {

	public SetSpawnCommand(CommandSender sender, String subCommand, String[] arguments, boolean isSenderPlayer) {
		super(sender, subCommand, arguments, isSenderPlayer);
	}

	@Override
	protected void execute() {
		Player p = (Player) sender;
		SpawnManager spawnManager = SpawnMe.getInstance().getSpawnManager();

		// If no arguments or first argument is numeric id is "default", else is custom
		String id;
		if (subCommand == ""|| StringUtils.isNumeric(subCommand)) {
			id = "default";
		} else {
			id = subCommand;
		}

		// Count numeric args, if at least 3 there are coordinates in command, if at
		// least 5 there is yaw and pitch too
		Location location;

		List<Double> numericArgsList = Arrays.stream(arguments).filter(StringUtils::isNumeric).map(Double::valueOf)
				.collect(Collectors.toList());
		boolean coordinates = numericArgsList.size() >= 3;
		boolean direction = numericArgsList.size() >= 5;

		// Sets location
		if (!coordinates) {
			location = p.getLocation();
		} else if (coordinates && !direction) {
			location = new Location(p.getWorld(), numericArgsList.get(0), numericArgsList.get(1),
					numericArgsList.get(2), p.getLocation().getYaw(), p.getLocation().getPitch());
		} else {
			location = new Location(p.getWorld(), numericArgsList.get(0), numericArgsList.get(1),
					numericArgsList.get(2), numericArgsList.get(3).floatValue(), numericArgsList.get(4).floatValue());
		}

		spawnManager.addSpawn(id, new LocationWrapper(location));
		p.sendMessage(Libs.getLocale().get("messages.command.spawn-set"));
	}

	@Override
	protected boolean isOnlyPlayer() {
		return true;
	}

	@Override
	protected Permission getPermission() {
		return new Permission("spawnme.admin.setspawn");
	}

}
