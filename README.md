# sql2o-spigot

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

sql2o-spigot is a simple library plugin for Spigot, that will help you to create and manage your SQL connections with ease. Ported from [**sql2o-nukkit**](https://github.com/hteppl/sql2o-nukkit).

## Build JAR File

```shell
$ git clone https://github.com/endchest/sql2o-spigot
$ cd sql2o-spigot
$ mvn clean package
```

## How to install

If any plugin requires a sql2o-spigot, you just need to download and put it in `plugins` folder. Usually it will be
enough. Also, you can configure some default database settings in `config.yml`.

### Maven

```xml
<repository>
    <id>endchest-releases</id>
    <url>https://repo.endchest.ru/releases</url>
</repository>
```

```xml
<dependency>
    <groupId>me.seetch</groupId>
    <artifactId>sql2o-spigot</artifactId>
    <version>1.0.0</version>
</dependency>
```

### Gradle

```groovy
maven {
    name "endchestReleases"
    url "https://repo.endchest.ru/releases"
}
```

```groovy
dependencies {
    implementation "me.seetch:sql2o-spigot:1.0.0"
}
```

## Configuration

Default plugin `config.yml` settings.

```yaml
# Hikari connection pool settings (https://github.com/brettwooldridge/HikariCP)
hikari:
  min-idle: 10               # Minimum number of idle connections in the pool
  maximum-pool-size: 10      # Maximum number of connections in the pool
  max-lifetime: 1800000      # Maximum lifetime of a connection in milliseconds (30 minutes)
  connection-timeout: 30000  # Maximum time to wait for a connection in milliseconds (30 seconds)
  validation-timeout: 5000   # Timeout for connection validation in milliseconds (5 seconds)
  idle-timeout: 600000       # Maximum idle time for a connection in milliseconds (10 minutes)
  auto-commit: true          # Should connections auto-commit by default
  keepalive-time: 0          # Keepalive time in milliseconds (0 = disabled)
```

## How to use

Firstly we recommend to read:

- [*sql2o Developer Guide*](https://www.sql2o.org/)
- [*HikariCP Configuration*](https://github.com/brettwooldridge/HikariCP#gear-configuration-knobs-baby)

Basic example of MySQLDatabase class:

```java
import me.seetch.data.database.MySQLDatabase;
import org.sql2o.Connection;

public class MyDatabase extends MySQLDatabase {

    public MyDatabase() {
        super("localhost", "mydb", "user", "password"); // defaults to port 3306

        // Example: create table and insert data safely
        try (Connection con = this.openConnection()) {
            // Create table
            con.createQuery("""
                        CREATE TABLE IF NOT EXISTS users (
                            id SERIAL PRIMARY KEY,
                            name VARCHAR(100) NOT NULL,
                            email VARCHAR(100) UNIQUE NOT NULL
                        )
                    """).executeUpdate();

            // Insert data with parameters (safe)
            con.createQuery("INSERT INTO users (name, email) VALUES (:name, :email)")
                    .addParameter("name", "Alice")
                    .addParameter("email", "alice@example.com")
                    .executeUpdate();
        }
    }
}
```

Basic example of PostgreSQLDatabase class:

```java
import me.seetch.data.database.PostgreSQLDatabase;
import org.sql2o.Connection;

public class MyPostgresDatabase extends PostgreSQLDatabase {

    public MyPostgresDatabase() {
        super("localhost", "mydb", "user", "password"); // default port 5432

        try (Connection con = this.openConnection()) {
            con.createQuery("""
                        CREATE TABLE IF NOT EXISTS products (
                            id SERIAL PRIMARY KEY,
                            name VARCHAR(100),
                            price NUMERIC(10,2)
                        )
                    """).executeUpdate();

            con.createQuery("INSERT INTO products (name, price) VALUES (:name, :price)")
                    .addParameter("name", "Sword")
                    .addParameter("price", 29.99)
                    .executeUpdate();
        }
    }
}
```

Example of SQLite database class:

```java
import me.seetch.data.database.SQLiteDatabase;
import org.sql2o.Connection;

public class MySQLiteDatabase extends SQLiteDatabase {

    public MySQLiteDatabase() {
        super("data/mydb"); // folder + filename

        try (Connection con = this.openConnection()) {
            con.createQuery("""
                        CREATE TABLE IF NOT EXISTS items (
                            id INTEGER PRIMARY KEY AUTOINCREMENT,
                            name TEXT NOT NULL,
                            quantity INTEGER NOT NULL
                        )
                    """).executeUpdate();

            con.createQuery("INSERT INTO items (name, quantity) VALUES (:name, :quantity)")
                    .addParameter("name", "Diamond")
                    .addParameter("quantity", 5)
                    .executeUpdate();
        }
    }
}
```

Using transactions:

```java
try (Connection tx = database.beginTransaction()) {
    tx.createQuery("INSERT INTO users (name, email) VALUES (:name, :email)")
      .addParameter("name", "Bob")
      .addParameter("email", "bob@example.com")
      .executeUpdate();

    tx.createQuery("UPDATE users SET email = :email WHERE name = :name")
      .addParameter("name", "Alice")
      .addParameter("email", "alice_new@example.com")
      .executeUpdate();

    tx.commit(); // commit transaction
}
```

- Always use parameter binding (:param) to avoid SQL injection.
- Prefer try-with-resources so connections are closed automatically.
- Transactions can be used with beginTransaction() to group multiple queries.
- Hikari settings are configured via YAML (config.yml) and applied automatically via your Database classes.

## Libraries

[**sql2o-nukkit**](https://github.com/hteppl/sql2o-nukkit).

[**MariaDB Connector**](https://github.com/mariadb-corporation/mariadb-connector-j) MariaDB Connector/J is a Type 4 JDBC
driver. It was developed specifically as a lightweight JDBC connector for use with MariaDB and MySQL database servers.

[**sql2o**](https://github.com/aaberg/sql2o) A lightweight Java library for working with SQL databases. Provides clean, minimalistic ORM-style mapping between database rows and Java objects.

[**HikariCP**](https://github.com/brettwooldridge/HikariCP) is a "zero-overhead" production ready JDBC connection pool.
At roughly 130Kb, the library is very light.

## License

This project is licensed under the [MIT License](https://opensource.org/licenses/MIT)