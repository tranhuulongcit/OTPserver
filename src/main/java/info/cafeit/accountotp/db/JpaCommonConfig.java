package info.cafeit.accountotp.db;

import org.hibernate.dialect.Dialect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

import static java.lang.Boolean.TRUE;
import static org.hibernate.cfg.AvailableSettings.*;

@Configuration
@EnableSpringDataWebSupport
public abstract class JpaCommonConfig {


    private static final Logger logger = LoggerFactory.getLogger(JpaCommonConfig.class);
    public static final String UNDEFINED = "**UNDEFINED**";
    public static final String CONNECTION_CHAR_SET = "hibernate.connection.charSet";
    public static final String VALIDATOR_APPLY_TO_DDL = "hibernate.validator.apply_to_ddl";
    public static final String VALIDATOR_AUTOREGISTER_LISTENERS = "hibernate.validator.autoregister_listeners";
    public static final String ZERO_DATETIME_BEHAVIOR = "hibernate.connection.zeroDateTimeBehavior";

    @Autowired
    protected Environment environment;

    @Value("#{ environment['entity.package'] }")
    private String entityPackage;

    @Bean(destroyMethod = "close")
    public abstract DataSource dataSource();

    @Bean
    @Qualifier(value = "jpaTransactionManager")
    public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }


    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(false);
        vendorAdapter.setDatabasePlatform(getDatabaseDialect().getName());
        vendorAdapter.setShowSql(true);

        LocalContainerEntityManagerFactoryBean factory =
                new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan(entityPackage);
        factory.setDataSource(dataSource());
        if (getJpaProperties() != null) {
            factory.setJpaProperties(getJpaProperties());
        }
        return factory;
    }

    protected abstract Class<? extends Dialect> getDatabaseDialect();

    protected Properties getJpaProperties() {
        Properties properties = new Properties();
        properties.setProperty(HBM2DDL_AUTO, getHbm2ddl());
        properties.setProperty(HBM2DDL_IMPORT_FILES_SQL_EXTRACTOR, getH2SqlExtractor());
        properties.setProperty(GENERATE_STATISTICS, TRUE.toString());
        properties.setProperty(SHOW_SQL, getShowSql());
        properties.setProperty(ZERO_DATETIME_BEHAVIOR, getZeroDateTimeBehavior());
        properties.setProperty(FORMAT_SQL, TRUE.toString());
        properties.setProperty(USE_SQL_COMMENTS, TRUE.toString());
        properties.setProperty(CONNECTION_CHAR_SET, getHibernateCharSet());
        //properties.setProperty(NAMING_STRATEGY, ImprovedNamingStrategy.class.getName());
        return properties;
    }

    // region Get Properties from datasource .properties file
    private String getZeroDateTimeBehavior() {
        return environment.getProperty("hibernate.connection.zeroDateTimeBehavior", UNDEFINED);
    }

    public String getDatabaseName() {
        return environment.getProperty("database.name", UNDEFINED);
    }

    public String getHost() {
        return environment.getProperty("database.host", UNDEFINED);
    }

    public String getPort() {
        return environment.getProperty("database.port", UNDEFINED);
    }

    public String getUrl() {
        return environment.getProperty("database.url", UNDEFINED);
    }

    public String getUser() {
        return environment.getProperty("database.username", UNDEFINED);
    }

    public String getPassword() {
        return environment.getProperty("database.password", UNDEFINED);
    }

    public String getDriverClassName() {
        return environment.getProperty("database.driverClassName", UNDEFINED);
    }

    public String getDialect() {
        return environment.getProperty("database.dialect", UNDEFINED);
    }

    public String getDatabaseVendor() {
        return environment.getProperty("database.vendor", UNDEFINED);
    }

    public String getHbm2ddl() {
        return environment.getProperty("database.hbm2ddl.auto", "none");
    }

    public String getShowSql() {
        return environment.getProperty("hibernate.showsql", "TRUE");
    }

    public String getHibernateCharSet() {
        return environment.getProperty("database.hibernateCharSet", "UTF-8");
    }

    public String getDatabaseValidationQuery() {
        return environment.getProperty("database.validation.query", UNDEFINED);
    }

    public String getH2SqlExtractor() {
        return environment.getProperty("hibernate.hbm2ddl.import_files_sql_extractor",UNDEFINED);
    }

    // endregion

}
