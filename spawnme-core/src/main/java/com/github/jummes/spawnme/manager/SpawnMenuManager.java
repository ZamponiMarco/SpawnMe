package com.github.jummes.spawnme.manager;

import org.bukkit.plugin.java.JavaPlugin;

import com.github.jummes.libs.model.ModelManager;
import com.github.jummes.spawnme.menu.SpawnMenu;
import com.google.common.collect.Lists;

import lombok.Getter;

@Getter
public class SpawnMenuManager extends ModelManager<SpawnMenu> {

    private SpawnMenu menu;

    public SpawnMenuManager(Class<SpawnMenu> classObject, String databaseType, JavaPlugin plugin) {
        super(classObject, databaseType, plugin);
        this.menu = database.loadObjects().stream().findAny().orElse(new SpawnMenu("Spawns", Lists.newArrayList()));
    }

    public void reloadData(){
        this.menu = database.loadObjects().stream().findAny().orElse(new SpawnMenu("Spawns", Lists.newArrayList()));
    }

}
