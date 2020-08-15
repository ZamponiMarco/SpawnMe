package com.github.jummes.spawnme.hook;

import com.earth2me.essentials.Essentials;
import com.github.jummes.libs.model.wrapper.LocationWrapper;
import com.github.jummes.spawnme.core.SpawnMe;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class EssentialsSpawnHook {

    private Essentials essentials;

    public EssentialsSpawnHook() {
        this.essentials = SpawnMe.getPlugin(Essentials.class);
    }

    public void importFromEssentialsSpawn() {
        File spawnFile = new File(essentials.getDataFolder(), "spawn.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(spawnFile);
        ConfigurationSection spawnsSection = config.getConfigurationSection("spawns");
        if (spawnsSection != null) {
            spawnsSection.getKeys(false).forEach(key -> {
                SpawnMe.getInstance().getSpawnManager().addSpawn(key, new LocationWrapper(
                        deserializeLocation(spawnsSection.getConfigurationSection(key))));
            });
        }
    }

    private Location deserializeLocation(ConfigurationSection section) {
        World world = Bukkit.getWorld(section.getString("world"));
        double x = section.getDouble("x");
        double y = section.getDouble("y");
        double z = section.getDouble("z");
        float yaw = (float) section.getDouble("yaw");
        float pitch = (float) section.getDouble("pitch");
        return new Location(world, x, y, z, yaw, pitch);
    }
}
