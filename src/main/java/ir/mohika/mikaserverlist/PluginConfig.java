package ir.mohika.mikaserverlist;

import com.electronwill.nightconfig.core.CommentedConfig;
import com.electronwill.nightconfig.core.Config;
import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.velocitypowered.api.util.ServerLink;
import lombok.Getter;
import net.kyori.adventure.text.minimessage.MiniMessage;

import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Getter
public class PluginConfig {
  private final List<ServerLink> links;

  private PluginConfig(List<ServerLink> links) {
    this.links = links;
  }

  public static PluginConfig read(Path path) {
    URL defaultConfigLocation = PluginConfig.class.getClassLoader().getResource("config.toml");
    if (defaultConfigLocation == null) {
      throw new RuntimeException("Default configuration file does not exist.");
    }

    try (final CommentedFileConfig config =
        CommentedFileConfig.builder(path)
            .defaultData(defaultConfigLocation)
            .autosave()
            .preserveInsertionOrder()
            .sync()
            .build()) {
      config.load();

      MiniMessage mm = MiniMessage.miniMessage();

      CommentedConfig serversConfig = config.get("servers");
      List<ServerLink> links = new ArrayList<>();
      for (CommentedConfig.Entry entry : serversConfig.entrySet()) {
        Config serverConfig = entry.getValue();
        String name = serverConfig.get("name");
        String url = serverConfig.get("url");
        links.add(ServerLink.serverLink(mm.deserialize(name), url));
      }

      return new PluginConfig(links);
    }
  }
}
