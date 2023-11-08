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
    basePackages = ["org.project.async.buffer.core.repository.buffer"],
    transactionManagerRef = "bufferTransactionManager",
    entityManagerFactoryRef = "bufferEntityManager"
)
class BufferDataSourceConfig {

    @Bean("bufferDataSource")
    fun bufferDataSource(
        @Qualifier("bufferSourceProperties") dataSourceProperties: DataSourceProperties,
    ): DataSource {
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    @Bean("bufferEntityManager")
    fun bufferEntityManager(
        @Qualifier("bufferDataSource") dataSource: DataSource,
    ): LocalContainerEntityManagerFactoryBean {
        val factory = LocalContainerEntityManagerFactoryBean()
        val vendorAdapter = HibernateJpaVendorAdapter()

        factory.persistenceUnitName = "bufferUnit"
        factory.dataSource = dataSource;
        factory.setPackagesToScan("org.project.async.buffer.core.model.buffer")
        factory.jpaVendorAdapter = vendorAdapter

        return factory
    }

    @Bean("bufferTransactionManager")
    fun batchTransactionManager(
        @Qualifier("bufferEntityManager") entityManager: EntityManagerFactory,
    ): PlatformTransactionManager {
        return JpaTransactionManager(entityManager)
    }

}
