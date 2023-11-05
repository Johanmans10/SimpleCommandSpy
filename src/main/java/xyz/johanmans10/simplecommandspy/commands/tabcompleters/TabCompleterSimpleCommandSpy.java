package xyz.johanmans10.simplecommandspy.commands.tabcompleters;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.johanmans10.simplecommandspy.constants.Permissions;

import java.util.ArrayList;
import java.util.List;

public class TabCompleterSimpleCommandSpy implements TabCompleter {
    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> ret = new ArrayList<>();
        if (!sender.hasPermission(Permissions.SPY)) return ret;
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("")) {
                ret.add("toggle");
                if (sender.hasPermission(Permissions.RELOAD))
                    ret.add("reload");
            } else {
                if ("toggle".startsWith(args[0]))
                    ret.add("toggle".substring(args[0].length()));
                if (sender.hasPermission(Permissions.RELOAD) && "reload".startsWith(args[0]))
                    ret.add("reload".substring(args[0].length()));
            }
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("toggle") && sender.hasPermission(Permissions.SPY_OTHERS))
                return null;
        }
        return ret;
    }
}
