package me.seetch.data;

import lombok.Getter;
import me.seetch.data.utils.Settings;
import org.bukkit.plugin.java.JavaPlugin;

public class DataManager extends JavaPlugin {

    @Getter
    private static Settings settings;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        settings = new Settings(this.getConfig());
    }
}