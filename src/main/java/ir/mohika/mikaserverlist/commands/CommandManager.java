package ir.mohika.mikaserverlist.commands;

import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import com.velocitypowered.api.command.CommandSource;
import io.leangen.geantyref.TypeToken;
import ir.mohika.mikaserverlist.MikaServerList;
import org.incendo.cloud.SenderMapper;
import org.incendo.cloud.annotations.AnnotationParser;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.incendo.cloud.velocity.CloudInjectionModule;
import org.incendo.cloud.velocity.VelocityCommandManager;

public class CommandManager {
  private final VelocityCommandManager<CommandSource> commandManager;

  public CommandManager(MikaServerList plugin, Injector injector) {

    final Injector childInjector =
        injector.createChildInjector(
            new CloudInjectionModule<>(
                CommandSource.class,
                ExecutionCoordinator.simpleCoordinator(),
                SenderMapper.identity()));
    this.commandManager = childInjector.getInstance(Key.get(new TypeLiteral<>() {}));
    AnnotationParser<CommandSource> annotationParser =
        new AnnotationParser<>(commandManager, CommandSource.class);

    commandManager
        .parameterInjectorRegistry()
        .registerInjector(TypeToken.get(MikaServerList.class), (context, annotations) -> plugin);

    annotationParser.parse(new Commands());
  }
}
