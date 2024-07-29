package ir.mohika.mikaserverlist.commands;

import com.velocitypowered.api.command.CommandSource;
import ir.mohika.mikaserverlist.MikaServerList;
import net.kyori.adventure.text.Component;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.Permission;

public class Commands {
  @Command("mikaserverlist reload")
  @Command("msl reload")
  @Permission(Permissions.reload)
  public void reload(CommandSource sender, MikaServerList plugin) {
    plugin.reloadConfig();
    sender.sendMessage(
        Component.text("Config reloaded! Players need to rejoin for changes to take effect."));
  }
}
