package com.github.jummes.spawnme.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import com.github.jummes.libs.command.AbstractCommand;
import com.github.jummes.libs.gui.model.ModelCollectionInventoryHolder;
import com.github.jummes.spawnme.core.SpawnMe;

public class SpawnMeSpawnsCommand extends AbstractCommand {

    public SpawnMeSpawnsCommand(CommandSender sender, String subCommand, String[] arguments, boolean isSenderPlayer) {
        super(sender, subCommand, arguments, isSenderPlayer);
    }

    @Override
    protected void execute() {
        Player p = (Player) sender;
        try {
            p.openInventory(new ModelCollectionInventoryHolder(SpawnMe.getInstance(),
                    SpawnMe.getInstance().getSpawnManager(), "spawns").getInventory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected boolean isOnlyPlayer() {
        return true;
    }

    @Override
    protected Permission getPermission() {
        return new Permission("spawnme.admin.spawns");
    }

}
