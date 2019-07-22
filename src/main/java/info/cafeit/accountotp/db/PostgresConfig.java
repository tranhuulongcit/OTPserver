package info.cafeit.accountotp.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.PostgreSQL9Dialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.init.DatabasePopulator;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:postgres.properties")
public class PostgresConfig extends JpaCommonConfig {

    @Bean(destroyMethod = "close")
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(getDriverClassName());
        config.setJdbcUrl(getUrl());
        config.setUsername(getUser());
        config.setPassword(getPassword());
        return new HikariDataSource(config);
    }

    @Override
    protected Class<? extends Dialect> getDatabaseDialect() {
        return PostgreSQL9Dialect.class;
    }

    @Bean
    public DatabasePopulator databasePopulator() {
        return null;
    }

}

