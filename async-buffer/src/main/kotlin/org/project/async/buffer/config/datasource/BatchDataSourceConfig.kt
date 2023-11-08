package org.project.async.buffer.config.datasource

import jakarta.persistence.EntityManagerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource

@Configuration
@EnableJpaRepositories(
    basePackages = ["org.springframework.batch.core.repository"],
    entityManagerFactoryRef = "batchEntityManager",
    transactionManagerRef = "batchTransactionManager"
)
class BatchDataSourceConfig {

    @Bean
    fun batchDataSource(
        @Qualifier("batchSourceProperties") dataSourceProperties: DataSourceProperties,
    ): DataSource {
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    @Bean("batchEntityManager")
    fun entityManagerFactory(
        @Qualifier("batchDataSource") dataSource: DataSource,
    ): LocalContainerEntityManagerFactoryBean {
        val factoryBean = LocalContainerEntityManagerFactoryBean()
        val vendorAdapter = HibernateJpaVendorAdapter()

        factoryBean.persistenceUnitName = "batchUnit"
        factoryBean.dataSource = dataSource
        factoryBean.setPackagesToScan("org.springframework.batch.core")
        factoryBean.jpaVendorAdapter = vendorAdapter

        return factoryBean
    }

    @Bean("batchTransactionManager")
    fun batchTransactionManager(
        @Qualifier("batchEntityManager") entityManager: EntityManagerFactory,
    ): PlatformTransactionManager {
        return JpaTransactionManager(entityManager)
    }
}