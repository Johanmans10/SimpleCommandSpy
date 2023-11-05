package xyz.johanmans10.simplecommandspy.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import xyz.johanmans10.simplecommandspy.SimpleCommandSpy;
import xyz.johanmans10.simplecommandspy.constants.Paths;
import xyz.johanmans10.simplecommandspy.constants.Permissions;
import xyz.johanmans10.simplecommandspy.utilities.Configuration;
import xyz.johanmans10.simplecommandspy.utilities.PlaceholderUtils;

import java.util.List;

public class ListenerCommand implements Listener {

    private final Configuration configuration;

    public ListenerCommand() {
        configuration = SimpleCommandSpy.getInstance().getConfiguration();
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onCommand(PlayerCommandPreprocessEvent event) {
        if (event.getPlayer().hasPermission(Permissions.EXEMPT)) return;

        List<String> ignoredCommands = configuration.getConfig().getStringList(Paths.CONFIG_IGNORED_COMMANDS);

        for (String ignoredCommand : ignoredCommands) {
            if (event.getMessage().substring(1).split(" ")[0].equalsIgnoreCase(ignoredCommand)) return;
        }

        String spyFormat = configuration.getConfig().getString(Paths.CONFIG_FORMAT_SPY, "{NAME}: {COMMAND}");

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.equals(event.getPlayer())) continue;
            if (!player.hasPermission(Permissions.SPY)) {
                configuration.getData().set(
                        PlaceholderUtils
                                .of(Paths.DATA_SCS_TOGGLE)
                                .uuid(player.getUniqueId())
                                .toString(),
                        null);
                continue;
            }
            if (!configuration.getData().getBoolean(PlaceholderUtils.of(Paths.DATA_SCS_TOGGLE).uuid(player.getUniqueId()).toString(), false)) continue;
            player.sendMessage(
                    PlaceholderUtils
                            .of(spyFormat)
                            .name(event.getPlayer().getName())
                            .uuid(event.getPlayer().getUniqueId())
                            .command(event.getMessage().substring(1))
                            .shortPrefix()
                            .color()
                            .toString());
        }
        configuration.saveData();
    }
}
