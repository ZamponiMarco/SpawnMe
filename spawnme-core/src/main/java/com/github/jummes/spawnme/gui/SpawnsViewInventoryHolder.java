package com.github.jummes.spawnme.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.jummes.libs.core.Libs;
import com.github.jummes.libs.gui.PluginInventoryHolder;
import com.github.jummes.libs.util.ItemUtils;
import com.github.jummes.libs.util.MessageUtils;
import com.github.jummes.spawnme.core.SpawnMe;
import com.github.jummes.spawnme.menu.SpawnItem;
import com.github.jummes.spawnme.menu.SpawnMenu;

public class SpawnsViewInventoryHolder extends PluginInventoryHolder {

	private static final String NEXT_PAGE_ITEM = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTdiMDNiNzFkM2Y4NjIyMGVmMTIyZjk4MzFhNzI2ZWIyYjI4MzMxOWM3YjYyZTdkY2QyZDY0ZDk2ODIifX19==";
	private static final String PREVIOUS_PAGE_ITEM = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDgzNDhhYTc3ZjlmYjJiOTFlZWY2NjJiNWM4MWI1Y2EzMzVkZGVlMWI5MDVmM2E4YjkyMDk1ZDBhMWYxNDEifX19==";

	private final static int OBJECTS_NUMBER = 50;

	private int page;
	private Player player;

	public SpawnsViewInventoryHolder(JavaPlugin plugin, PluginInventoryHolder parent, Player player, int page) {
		super(plugin, parent);
		this.player = player;
		this.page = page;
	}

	@Override
	protected void initializeInventory() {
		SpawnMenu menu = SpawnMe.getInstance().getSpawnMenuManager().getMenu();
		List<SpawnItem> spawns = menu.getSpawns().stream()
				.filter(spawn -> player.hasPermission("spawnme.spawn." + spawn.getSpawnId()))
				.collect(Collectors.toList());
		int spawnsCount = spawns.size();
		int invSize;

		List<SpawnItem> toList;
		if (spawnsCount > OBJECTS_NUMBER) {
			invSize = 54;
			toList = spawns.subList((page - 1) * OBJECTS_NUMBER, Math.min(spawnsCount, page * OBJECTS_NUMBER));
			int maxPage = (int) Math.ceil((spawnsCount > 0 ? spawnsCount : 1) / (double) OBJECTS_NUMBER);

			if (page != maxPage) {
				registerClickConsumer(52,
						ItemUtils.getNamedItem(Libs.getWrapper().skullFromValue(NEXT_PAGE_ITEM),
								MessageUtils.color("&6&lNext page"), new ArrayList<String>()),
						e -> e.getWhoClicked().openInventory(
								new SpawnsViewInventoryHolder(plugin, parent, player, page + 1).getInventory()));
			}
			if (page != 1) {
				registerClickConsumer(51,
						ItemUtils.getNamedItem(Libs.getWrapper().skullFromValue(PREVIOUS_PAGE_ITEM),
								MessageUtils.color("&6&lPrevious page"), new ArrayList<String>()),
						e -> e.getWhoClicked().openInventory(
								new SpawnsViewInventoryHolder(plugin, parent, player, page - 1).getInventory()));
			}
		} else {
			invSize = ((spawnsCount + 1) / 9 * 9) + 9;
			toList = spawns;
		}
		this.inventory = Bukkit.createInventory(this, invSize, menu.getTitle());
		registerSpawnSlots(toList);
		registerClickConsumer(invSize - 1, getBackItem(), getBackConsumer());
		fillInventoryWith(Material.GRAY_STAINED_GLASS_PANE);

	}

	public void registerSpawnSlots(List<SpawnItem> toList) {
		IntStream.range(0, toList.size()).forEach(i -> {
			registerClickConsumer(i, toList.get(i).getGUIItem(), e -> {
				player.teleport(toList.get(i).getSpawn().getLocation().getWrapped(), TeleportCause.PLUGIN);
				player.sendMessage(Libs.getLocale().get("messages.spawn.success"));
			});
		});
	}

}
