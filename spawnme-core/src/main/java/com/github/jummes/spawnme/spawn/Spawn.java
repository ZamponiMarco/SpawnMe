package com.github.jummes.spawnme.spawn;

import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.lang.RandomStringUtils;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.github.jummes.libs.annotation.Serializable;
import com.github.jummes.libs.core.Libs;
import com.github.jummes.libs.model.Model;
import com.github.jummes.libs.model.wrapper.LocationWrapper;
import com.github.jummes.libs.util.ItemUtils;
import com.github.jummes.spawnme.core.SpawnMe;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@SerializableAs("Spawn")
public class Spawn implements Model {

    private static final String ID_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDU4NGNmN2Q3OWYxYWViMjU1NGMxYmZkNDZlNmI3OGNhNmFlM2FhMmEyMTMyMzQ2YTQxMGYxNWU0MjZmMzEifX19=";
    private static final String TARGET_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzc4N2I3YWZiNWE1OTk1Mzk3NWJiYTI0NzM3NDliNjAxZDU0ZDZmOTNjZWFjN2EwMmFjNjlhYWU3ZjliOCJ9fX0==";

    @EqualsAndHashCode.Include
    @Serializable(headTexture = ID_HEAD, description = "gui.spawn.id")
    private final String id;
    @Serializable(headTexture = TARGET_HEAD, description = "gui.spawn.location")
    private LocationWrapper location;

    public Spawn(Player p) {
        this(RandomStringUtils.randomAlphabetic(6), new LocationWrapper(p.getLocation().getBlock().getLocation()));
    }

    // ---

    public static Spawn deserialize(Map<String, Object> map) {
        return new Spawn((String) map.get("id"), (LocationWrapper) map.get("location"));
    }

    @Override
    public ItemStack getGUIItem() {
        return ItemUtils.getNamedItem(Libs.getWrapper().skullFromValue(TARGET_HEAD), id, new ArrayList<String>());
    }

    @Override
    public void onRemoval() {
        SpawnMe.getInstance().getSpawnMenuManager().getMenu().getSpawns()
                .removeIf(spawn -> spawn.getSpawnId().equals(id));
    }
}
