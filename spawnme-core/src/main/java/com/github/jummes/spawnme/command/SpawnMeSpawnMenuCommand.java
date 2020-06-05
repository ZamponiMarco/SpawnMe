package com.github.jummes.spawnme.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import com.github.jummes.libs.command.AbstractCommand;
import com.github.jummes.libs.gui.model.ModelObjectInventoryHolder;
import com.github.jummes.libs.model.ModelPath;
import com.github.jummes.spawnme.core.SpawnMe;
import com.github.jummes.spawnme.menu.SpawnMenu;

public class SpawnMeSpawnMenuCommand extends AbstractCommand {

    public SpawnMeSpawnMenuCommand(CommandSender sender, String subCommand, String[] arguments,
                                   boolean isSenderPlayer) {
        super(sender, subCommand, arguments, isSenderPlayer);
    }

    @Override
    protected void execute() {
        Player p = (Player) sender;
        p.openInventory(new ModelObjectInventoryHolder(SpawnMe.getInstance(), null, new ModelPath<SpawnMenu>(
                SpawnMe.getInstance().getSpawnMenuManager(), SpawnMe.getInstance().getSpawnMenuManager().getMenu()))
                .getInventory());
    }

    @Override
    protected boolean isOnlyPlayer() {
        return true;
    }

    @Override
    protected Permission getPermission() {
        return new Permission("spawnme.admin.menu");
    }

}
