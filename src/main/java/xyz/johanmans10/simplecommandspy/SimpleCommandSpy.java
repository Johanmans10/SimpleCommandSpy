package xyz.johanmans10.simplecommandspy;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.johanmans10.simplecommandspy.commands.CommandSimpleCommandSpy;
import xyz.johanmans10.simplecommandspy.commands.tabcompleters.TabCompleterSimpleCommandSpy;
import xyz.johanmans10.simplecommandspy.listeners.ListenerCommand;
import xyz.johanmans10.simplecommandspy.utilities.Configuration;

@Getter
public final class SimpleCommandSpy extends JavaPlugin {

    @Getter
    private static SimpleCommandSpy instance;

    private Configuration configuration;

    @Override
    public void onEnable() {
        instance = this;
        configuration = new Configuration();

        Bukkit.getPluginManager().registerEvents(new ListenerCommand(), this);

        PluginCommand mainCommand = Bukkit.getPluginCommand("simplecommandspy");
        if (mainCommand == null) {
            Bukkit.getLogger().severe("§cUnable to find main command! Shutting down plugin.");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        mainCommand.setExecutor(new CommandSimpleCommandSpy());
        mainCommand.setTabCompleter(new TabCompleterSimpleCommandSpy());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
