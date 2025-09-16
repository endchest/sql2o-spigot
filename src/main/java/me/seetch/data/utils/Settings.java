package me.seetch.data.utils;

import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

@Getter
public class Settings {

    private final HikariSettings hikari;

    public Settings(FileConfiguration config) {
        this.hikari = new HikariSettings(config.getConfigurationSection("hikari"));
    }

    @Getter
    public static class HikariSettings {

        public final int minIdle;
        public final int maximumPoolSize;
        public final int maxLifetime;
        public final int connectionTimeout;
        public final int validationTimeout;
        public final int idleTimeout;
        public final boolean autoCommit;
        public final int keepaliveTime;

        public HikariSettings(ConfigurationSection section) {
            if (section == null) {
                // Default values if section doesn't exist
                this.maximumPoolSize = 10;
                this.minIdle = 10;
                this.maxLifetime = 1800000;
                this.connectionTimeout = 30000;
                this.validationTimeout = 5000;
                this.idleTimeout = 600000;
                this.autoCommit = true;
                this.keepaliveTime = 0;
            } else {
                this.maximumPoolSize = section.getInt("maximum-pool-size", 10);
                this.minIdle = section.getInt("min-idle", this.maximumPoolSize);
                this.maxLifetime = section.getInt("max-lifetime", 1800000);
                this.connectionTimeout = section.getInt("connection-timeout", 30000);
                this.validationTimeout = section.getInt("validation-timeout", 5000);
                this.idleTimeout = section.getInt("idle-timeout", 600000);
                this.autoCommit = section.getBoolean("auto-commit", true);
                this.keepaliveTime = section.getInt("keepalive-time", 0);
            }
        }
    }
}