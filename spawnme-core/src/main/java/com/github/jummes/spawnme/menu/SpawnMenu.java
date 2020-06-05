package com.github.jummes.spawnme.menu;

import java.util.List;
import java.util.Map;

import com.github.jummes.libs.annotation.Serializable;
import com.github.jummes.libs.model.Model;
import com.google.common.collect.Lists;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SpawnMenu implements Model {

    private static final String ID_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDU4NGNmN2Q3OWYxYWViMjU1NGMxYmZkNDZlNmI3OGNhNmFlM2FhMmEyMTMyMzQ2YTQxMGYxNWU0MjZmMzEifX19=";
    private static final String TARGET_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzc4N2I3YWZiNWE1OTk1Mzk3NWJiYTI0NzM3NDliNjAxZDU0ZDZmOTNjZWFjN2EwMmFjNjlhYWU3ZjliOCJ9fX0==";

    @Serializable(headTexture = ID_HEAD, description = "gui.spawn-menu.title")
    private String title;
    @Serializable(headTexture = TARGET_HEAD, description = "gui.spawn-menu.spawns")
    private List<SpawnItem> spawns;

    @SuppressWarnings("unchecked")
    public static SpawnMenu deserialize(Map<String, Object> map) {
        String title = (String) map.getOrDefault("title", "");
        List<SpawnItem> spawns = (List<SpawnItem>) map.getOrDefault("spawns", Lists.newArrayList());
        return new SpawnMenu(title, spawns);
    }

}
