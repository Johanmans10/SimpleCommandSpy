package xyz.johanmans10.simplecommandspy.utilities;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import xyz.johanmans10.simplecommandspy.SimpleCommandSpy;

import java.io.File;
import java.io.IOException;

public class Configuration {

    private final File dFile;
    @Getter
    private final FileConfiguration data;

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public Configuration() {
        SimpleCommandSpy plugin = SimpleCommandSpy.getInstance();
        plugin.saveDefaultConfig();

        if (!plugin.getDataFolder().exists())
            plugin.getDataFolder().mkdir();

        dFile = new File(plugin.getDataFolder(), "userdata.yml");
        if (!dFile.exists()) {
            try {
                dFile.createNewFile();
            }
            catch (IOException e) {
                Bukkit.getLogger().severe("§cCould not create userdata.yml!");
            }
        }

        data = YamlConfiguration.loadConfiguration(dFile);
    }

    public void saveData() {
        try {
            data.save(dFile);
        }
        catch (IOException e) {
            Bukkit.getLogger().severe("§cCould not save userdata.yml!");
        }
    }

    public FileConfiguration getConfig() {
        return SimpleCommandSpy.getInstance().getConfig();
    }
    public void reloadConfig() {
        SimpleCommandSpy.getInstance().reloadConfig();
    }
}

