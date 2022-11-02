package messenger;

import org.flywaydb.core.Flyway;
import org.glassfish.jersey.message.internal.DataSourceProvider;
import org.h2.jdbcx.JdbcDataSource;

import javax.sql.DataSource;

public class InMemoryDatabase {

    public static DataSource createTestDataSource(){
        var dataSource = new JdbcDataSource();

        dataSource.setUrl("jdbc:h2:mem:testDatabase;DB_CLOSE_DELAY=-1");
        var flyway = Flyway.configure().dataSource(dataSource).load();
        flyway.migrate();

        return dataSource;
    }

}
