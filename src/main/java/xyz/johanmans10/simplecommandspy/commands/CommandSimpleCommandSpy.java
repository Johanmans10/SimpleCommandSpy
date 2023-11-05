package xyz.johanmans10.simplecommandspy.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import xyz.johanmans10.simplecommandspy.SimpleCommandSpy;
import xyz.johanmans10.simplecommandspy.constants.Paths;
import xyz.johanmans10.simplecommandspy.constants.Permissions;
import xyz.johanmans10.simplecommandspy.utilities.Configuration;
import xyz.johanmans10.simplecommandspy.utilities.PlaceholderUtils;

public class CommandSimpleCommandSpy implements CommandExecutor {

    private final Configuration configuration;

    public CommandSimpleCommandSpy() {
        this.configuration = SimpleCommandSpy.getInstance().getConfiguration();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            args = new String[]{"toggle"};
        }

        if (args[0].equalsIgnoreCase("reload") && sender.hasPermission(Permissions.RELOAD)) {
            configuration.reloadConfig();
            sender.sendMessage(PlaceholderUtils.of("&aConfig reloaded!").prefix().color().toString());
            return true;
        } else if (args[0].equalsIgnoreCase("toggle") && sender.hasPermission(Permissions.SPY)) {
            if (args.length == 1) {
                if (!(sender instanceof Player player)) {
                    sender.sendMessage("§cThis is a player-only command!");
                    return true;
                }

                String dataPath = PlaceholderUtils
                        .of(Paths.DATA_SCS_TOGGLE)
                        .uuid(player.getUniqueId())
                        .toString();

                boolean newValue = !configuration.getData().getBoolean(dataPath, false);
                configuration.getData().set(dataPath, newValue);

                String message = configuration.getConfig().getString(newValue ? Paths.CONFIG_FORMAT_ENABLE : Paths.CONFIG_FORMAT_DISABLE, "CommandSpy has been toggled!");
                sender.sendMessage(
                        PlaceholderUtils
                                .of(message)
                                .name(player.getName())
                                .uuid(player.getUniqueId())
                                .prefix()
                                .color()
                                .toString());
                return true;
            } else if (sender.hasPermission(Permissions.SPY_OTHERS)) {
                Player player = Bukkit.getPlayer(args[1]);
                if (player == null) {
                    sender.sendMessage(
                            PlaceholderUtils
                                    .of("§cNo player was found with the name \"{NAME}\"")
                                    .name(args[1])
                                    .prefix()
                                    .color()
                                    .toString());
                    return true;
                }

                if (player.equals(sender)) {
                    sender.sendMessage(
                            PlaceholderUtils
                                    .of("§cUse §e/scs§c or §e/scs toggle§c to toggle SimpleCommandSpy for yourself!")
                                    .name(player.getName())
                                    .prefix()
                                    .color()
                                    .toString());
                    return true;
                }

                if (!player.hasPermission(Permissions.SPY)) {
                    sender.sendMessage(
                            PlaceholderUtils
                                    .of("§cPlayer {NAME} does not have permission to use SimpleCommandSpy!")
                                    .name(player.getName())
                                    .prefix()
                                    .color()
                                    .toString());
                    return true;
                }

                String dataPath = PlaceholderUtils
                        .of(Paths.DATA_SCS_TOGGLE)
                        .uuid(player.getUniqueId())
                        .toString();

                boolean newValue = !configuration.getData().getBoolean(dataPath, false);
                configuration.getData().set(dataPath, newValue);

                String messageSender = configuration.getConfig().getString(newValue ? Paths.CONFIG_FORMAT_ENABLE_OTHER : Paths.CONFIG_FORMAT_DISABLE_OTHER, "CommandSpy had been toggled for {NAME}!");
                String messagePlayer = configuration.getConfig().getString(newValue ? Paths.CONFIG_FORMAT_ENABLE_BY: Paths.CONFIG_FORMAT_DISABLE_BY, "CommandSpy had been toggled by {NAME}!");
                sender.sendMessage(
                        PlaceholderUtils
                                .of(messageSender)
                                .name(player.getName())
                                .uuid(player.getUniqueId())
                                .prefix()
                                .color()
                                .toString());
                player.sendMessage(
                        PlaceholderUtils
                                .of(messagePlayer)
                                .name(sender.getName())
                                .prefix()
                                .color()
                                .toString());
                return true;
            }
        }
        return false;
    }
}