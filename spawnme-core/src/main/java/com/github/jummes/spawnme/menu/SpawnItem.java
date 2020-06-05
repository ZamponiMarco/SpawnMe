package com.github.jummes.spawnme.menu;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.github.jummes.libs.annotation.Serializable;
import com.github.jummes.libs.model.Model;
import com.github.jummes.libs.model.ModelPath;
import com.github.jummes.libs.model.wrapper.ItemStackWrapper;
import com.github.jummes.libs.util.ItemUtils;
import com.github.jummes.spawnme.core.SpawnMe;
import com.github.jummes.spawnme.spawn.Spawn;
import com.google.common.collect.Lists;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SpawnItem implements Model {

    private static final String ITEM_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWZlOGU3ZjJkYjhlYWE4OGEwNDFjODlkNGMzNTNkMDY2Y2M0ZWRlZjc3ZWRjZjVlMDhiYjVkM2JhYWQifX19=";
    private static final String TARGET_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzc4N2I3YWZiNWE1OTk1Mzk3NWJiYTI0NzM3NDliNjAxZDU0ZDZmOTNjZWFjN2EwMmFjNjlhYWU3ZjliOCJ9fX0==";

    @Serializable(headTexture = TARGET_HEAD, fromList = "getSpawns", fromListMapper = "mapSpawns", description = "gui.spawn-item.spawn-id")
    private String spawnId;
    @Serializable(headTexture = ITEM_HEAD, description = "gui.spawn-item.item")
    private ItemStackWrapper displayItem;

    public SpawnItem() {
        this("", new ItemStackWrapper(new ItemStack(Material.STONE)));
    }

    public Spawn getSpawn() {
        return SpawnMe.getInstance().getSpawnManager().getSpawn(spawnId);
    }

    // ---

    public static SpawnItem deserialize(Map<String, Object> map) {
        String spawnId = (String) map.get("spawnId");
        ItemStackWrapper displayItem = (ItemStackWrapper) map.get("displayItem");
        return new SpawnItem(spawnId, displayItem);
    }

    @SuppressWarnings("unused")
    public static List<Object> getSpawns(ModelPath<?> path) {
        return SpawnMe.getInstance().getSpawnManager().getSpawns().stream().map(spawn -> spawn.getId())
                .collect(Collectors.toList());
    }

    public static Function<Object, ItemStack> mapSpawns() {
        return obj -> {
            return SpawnMe.getInstance().getSpawnManager().getSpawn((String) obj).getGUIItem();
        };
    }

    @Override
    public ItemStack getGUIItem() {
        return ItemUtils.getNamedItem(displayItem.getWrapped(), "&6" + spawnId, Lists.newArrayList());
    }
}