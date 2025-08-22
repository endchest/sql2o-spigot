package me.hteppl.data.utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.log4j.Log4j2;
import me.hteppl.data.DataManager;
import org.sql2o.Sql2o;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Log4j2
public class Create {

    /**
     * Helper method for PostgreSQL
     */
    public static Sql2o createPostgreSQL(String host, int port, String database, String user, String password, String properties) {
        StringBuilder jdbcUrl = new StringBuilder("jdbc:postgresql://")
                .append(host).append(":").append(port).append("/").append(database);
        if (properties != null && !properties.isBlank()) {
            jdbcUrl.append("?").append(properties);
        }
        return createDataSource("org.postgresql.Driver", jdbcUrl.toString(), user, password);
    }

    /**
     * Helper method for MariaDB
     */
    public static Sql2o createMariaDB(String host, int port, String database, String user, String password, String properties) {
        StringBuilder jdbcUrl = new StringBuilder("jdbc:mariadb://")
                .append(host).append(":").append(port).append("/").append(database);
        if (properties != null && !properties.isBlank()) {
            jdbcUrl.append("?").append(properties);
        }
        return createDataSource("org.mariadb.jdbc.Driver", jdbcUrl.toString(), user, password);
    }

    /**
     * Helper method for SQLite
     */
    public static Sql2o createSQLite(String folder, String database) {
        try {
            Class.forName("org.sqlite.JDBC");
            Files.createDirectories(Paths.get(folder));
        } catch (IOException | ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }

        return new Sql2o("jdbc:sqlite:" + folder + "/" + database + ".db", null, null);
    }

    public static Sql2o createDataSource(String driverClass, String jdbcUrl, String user, String password) {
        try {
            Class.forName(driverClass);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver not found: " + driverClass, e);
        }
        HikariConfig config = new HikariConfig();
        Settings.HikariSettings hikari = DataManager.getSettings().getHikari();

        // jdbcUrl
        config.setJdbcUrl(jdbcUrl);
        // credentials
        config.setUsername(user);
        config.setPassword(password);
        // HikariConfig variables
        config.setMinimumIdle(hikari.minIdle);
        config.setMaximumPoolSize(hikari.maximumPoolSize);
        config.setMaxLifetime(hikari.maxLifetime);
        config.setConnectionTimeout(hikari.connectionTimeout);
        config.setValidationTimeout(hikari.validationTimeout);
        config.setIdleTimeout(hikari.idleTimeout);
        config.setAutoCommit(hikari.autoCommit);
        config.setKeepaliveTime(hikari.keepaliveTime);
        return new Sql2o(new HikariDataSource(config));
    }

    public static Sql2o createFromDataSource(DataSource ds) {
        return new Sql2o(ds);
    }
}
