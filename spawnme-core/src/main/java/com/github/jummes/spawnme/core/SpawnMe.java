package com.github.jummes.spawnme.core;

import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.jummes.libs.command.PluginCommandExecutor;
import com.github.jummes.libs.core.Libs;
import com.github.jummes.libs.localization.PluginLocale;
import com.github.jummes.spawnme.command.SetSpawnCommand;
import com.github.jummes.spawnme.command.SpawnCommand;
import com.github.jummes.spawnme.command.SpawnMeHelpCommand;
import com.github.jummes.spawnme.command.SpawnMeReloadCommand;
import com.github.jummes.spawnme.command.SpawnMeSpawnMenuCommand;
import com.github.jummes.spawnme.command.SpawnMeSpawnsCommand;
import com.github.jummes.spawnme.command.SpawnsCommand;
import com.github.jummes.spawnme.listener.PlayerJoinListener;
import com.github.jummes.spawnme.listener.PlayerRespawnListener;
import com.github.jummes.spawnme.manager.SpawnManager;
import com.github.jummes.spawnme.manager.SpawnMenuManager;
import com.github.jummes.spawnme.menu.SpawnItem;
import com.github.jummes.spawnme.menu.SpawnMenu;
import com.github.jummes.spawnme.spawn.Spawn;
import com.google.common.collect.Lists;

import lombok.Getter;

@Getter
public class SpawnMe extends JavaPlugin {

	@Getter
	private static SpawnMe instance;

	private SpawnManager spawnManager;
	private SpawnMenuManager spawnMenuManager;

	static {
		Libs.registerSerializables();
		ConfigurationSerialization.registerClass(Spawn.class);
		ConfigurationSerialization.registerClass(SpawnMenu.class);
		ConfigurationSerialization.registerClass(SpawnItem.class);
	}

	public void onEnable() {
		instance = this;

		PluginLocale locale = new PluginLocale(this, Lists.newArrayList("en-US"), "en-US");

		Libs.initializeLibrary(this, locale);

		spawnManager = new SpawnManager(Spawn.class, "yaml", this);
		spawnMenuManager = new SpawnMenuManager(SpawnMenu.class, "yaml", this);

		registerListeners();
		setUpExecutors();
	}

	private void registerListeners() {
		getServer().getPluginManager().registerEvents(new PlayerRespawnListener(), this);
		getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
	}

	private void setUpExecutors() {
		PluginCommandExecutor spawnMeExecutor = new PluginCommandExecutor(SpawnMeHelpCommand.class, "help");
		spawnMeExecutor.registerCommand("spawns", SpawnMeSpawnsCommand.class);
		spawnMeExecutor.registerCommand("reload", SpawnMeReloadCommand.class);
		spawnMeExecutor.registerCommand("menu", SpawnMeSpawnMenuCommand.class);
		PluginCommandExecutor spawnExecutor = new PluginCommandExecutor(SpawnCommand.class, "");
		PluginCommandExecutor spawnsExecutor = new PluginCommandExecutor(SpawnsCommand.class, "");
		PluginCommandExecutor setSpawnExecutor = new PluginCommandExecutor(SetSpawnCommand.class, "");
		getCommand("spawnme").setExecutor(spawnMeExecutor);
		getCommand("spawn").setExecutor(spawnExecutor);
		getCommand("spawns").setExecutor(spawnsExecutor);
		getCommand("setspawn").setExecutor(setSpawnExecutor);
	}

}
