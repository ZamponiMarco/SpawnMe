package com.github.jummes.spawnme.manager;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.plugin.java.JavaPlugin;

import com.github.jummes.libs.model.ModelManager;
import com.github.jummes.libs.model.wrapper.LocationWrapper;
import com.github.jummes.spawnme.spawn.Spawn;

import lombok.Getter;

public class SpawnManager extends ModelManager<Spawn> {

    @Getter
    private Set<Spawn> spawns;

    public SpawnManager(Class<Spawn> classObject, String databaseType, JavaPlugin plugin) {
        super(classObject, databaseType, plugin);
        this.spawns = new HashSet<>(database.loadObjects());
    }

    public void addSpawn(String id, LocationWrapper location) {
        Spawn spawn = new Spawn(id, location);
        spawns.remove(spawn);
        spawns.add(spawn);
        database.saveObject(spawn);
    }

    public Spawn getSpawn(String id) {
        return spawns.stream().filter(spawn -> id.equals(spawn.getId())).findFirst().orElse(null);
    }

    public void reloadData() {
        this.spawns = new HashSet<>(database.loadObjects());
    }
}
