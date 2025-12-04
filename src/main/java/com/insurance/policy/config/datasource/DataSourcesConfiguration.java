package com.insurance.policy.config.datasource;

import com.insurance.policy.exception.BaseException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

import static com.insurance.policy.exception.BatchErrorType.GENERIC_ERROR;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.insurance.policy.data",
        entityManagerFactoryRef = "insuranceEntityManagerFactory",
        transactionManagerRef = "insuranceTransactionManager"
)
@SuppressWarnings({"PMD.AvoidUncheckedExceptionsInSignatures"})
public class DataSourcesConfiguration {
    @Bean("insuranceConfig")
    @ConfigurationProperties("spring.datasource")
    public HikariConfig appHikariConfig() {
        return new HikariConfig();
    }

    @Bean("insuranceDataSource")
    public HikariDataSource appDataSource(@Qualifier("insuranceConfig") HikariConfig hikariConfig) {
        return new HikariDataSource(hikariConfig);
    }

    @Bean(name = "insuranceEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(
        @Autowired EntityManagerFactoryBuilder builder,
        @Qualifier("insuranceDataSource") DataSource insuranceDataSource) {
        return builder
                .dataSource(insuranceDataSource)
                .packages("com.insurance.policy.data")
                .build();
    }

    @Bean("insuranceTransactionManager")
    public PlatformTransactionManager platformTransactionManager(
            @Qualifier("insuranceEntityManagerFactory")
            LocalContainerEntityManagerFactoryBean entityManagerFactoryBean)
            throws BaseException {
        if (entityManagerFactoryBean == null) {
            throw new BaseException(GENERIC_ERROR);
        } else {
            EntityManagerFactory emf = entityManagerFactoryBean.getObject();
            if (emf == null) {
                throw new BaseException(GENERIC_ERROR);
            } else {
                return new JpaTransactionManager(emf);
            }
        }
    }
}