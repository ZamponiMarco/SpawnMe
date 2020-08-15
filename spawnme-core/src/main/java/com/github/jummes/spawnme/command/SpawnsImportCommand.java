package com.github.jummes.spawnme.command;

import com.github.jummes.libs.command.AbstractCommand;
import com.github.jummes.libs.core.Libs;
import com.github.jummes.spawnme.core.SpawnMe;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;

public class SpawnsImportCommand extends AbstractCommand {
    public SpawnsImportCommand(CommandSender sender, java.lang.String subCommand, java.lang.String[] arguments, boolean isSenderPlayer) {
        super(sender, subCommand, arguments, isSenderPlayer);
    }

    @Override
    protected void execute() {
        if (SpawnMe.getInstance().getEssentialsSpawnHook() == null) {
            sender.sendMessage(Libs.getLocale().get("messages.command.essentials-not-installed"));
            return;
        }
        SpawnMe.getInstance().getEssentialsSpawnHook().importFromEssentialsSpawn();
        sender.sendMessage(Libs.getLocale().get("messages.command.essentials-spawns-imported"));
    }

    @Override
    protected boolean isOnlyPlayer() {
        return false;
    }

    @Override
    protected Permission getPermission() {
        return new Permission("spawnme.admin.import");
    }
}
