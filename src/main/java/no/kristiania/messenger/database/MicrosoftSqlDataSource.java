package no.kristiania.messenger.database;

import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class MicrosoftSqlDataSource {
    public static DataSource getDataSource() throws Exception {
        var properties = new Properties();

        var filePath = new File("application.properties");

        try (var reader = new FileReader(filePath.toString())) {
            properties.load(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        var dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(properties.getProperty("jdbc.url"));
        dataSource.setUsername(properties.getProperty("jdbc.username"));
        dataSource.setPassword(properties.getProperty("jdbc.password"));
        dataSource.setConnectionTimeout(2000);
        Flyway.configure().dataSource(dataSource).load().migrate();
        return dataSource;
    }
}
