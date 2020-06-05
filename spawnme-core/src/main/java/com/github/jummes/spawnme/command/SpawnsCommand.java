package com.github.jummes.spawnme.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import com.github.jummes.libs.command.AbstractCommand;
import com.github.jummes.libs.core.Libs;
import com.github.jummes.spawnme.core.SpawnMe;
import com.github.jummes.spawnme.gui.SpawnsViewInventoryHolder;

public class SpawnsCommand extends AbstractCommand {

    public SpawnsCommand(CommandSender sender, String subCommand, String[] arguments, boolean isSenderPlayer) {
        super(sender, subCommand, arguments, isSenderPlayer);
    }

    @Override
    protected void execute() {
        Player p = (Player) sender;
        if (!SpawnMe.getInstance().getSpawnMenuManager().getMenu().getSpawns().isEmpty()) {
            p.openInventory(new SpawnsViewInventoryHolder(SpawnMe.getInstance(), null, p, 1).getInventory());
        } else {
            p.sendMessage(Libs.getLocale().get("messages.command.menu-not-implemented"));
        }
    }

    @Override
    protected boolean isOnlyPlayer() {
        return true;
    }

    @Override
    protected Permission getPermission() {
        return new Permission("spawnme.spawn.spawns");
    }

}
