package ir.mohika.mikaserverlist;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.velocitypowered.api.event.EventManager;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import ir.mohika.mikaserverlist.commands.CommandManager;
import lombok.Getter;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import java.nio.file.Path;

@Plugin(id = "mikaserverlist", name = "mikaServerList", version = BuildConstants.VERSION)
public class MikaServerList {

  @Getter private final ProxyServer proxyServer;
  @Getter private final ComponentLogger logger;
  private final Injector injector;
  private final EventManager eventManager;
  @Getter private final Path dataDirectory;

  @Getter private PluginConfig config;

  @Inject
  public MikaServerList(
      ProxyServer proxyServer,
      ComponentLogger logger,
      Injector injector,
      EventManager eventManager,
      @DataDirectory Path dataDirectory) {
    this.proxyServer = proxyServer;
    this.logger = logger;
    this.injector = injector;
    this.eventManager = eventManager;
    this.dataDirectory = dataDirectory;
  }

  @Subscribe
  public void onProxyInitialization(ProxyInitializeEvent event) {
    Path configFile = dataDirectory.resolve("config.toml");
    config = PluginConfig.read(configFile);

    new CommandManager(this, injector);

    eventManager.register(this, new Listeners(this));
  }

  public void reloadConfig() {
    Path configFile = dataDirectory.resolve("config.toml");
    config = PluginConfig.read(configFile);
  }
}
