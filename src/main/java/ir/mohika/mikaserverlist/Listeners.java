package ir.mohika.mikaserverlist;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PostLoginEvent;
import com.velocitypowered.api.network.ProtocolVersion;
import com.velocitypowered.api.proxy.Player;

public class Listeners {
  private final MikaServerList plugin;

  public Listeners(MikaServerList plugin) {
    this.plugin = plugin;
  }

  @Subscribe
  public void onPostLogin(PostLoginEvent event) {
    Player player = event.getPlayer();
    ProtocolVersion protocolVersion = player.getProtocolVersion();
    if (protocolVersion.lessThan(ProtocolVersion.MINECRAFT_1_21)) {
      return;
    }
    player.setServerLinks(plugin.getConfig().getLinks());
  }
}
