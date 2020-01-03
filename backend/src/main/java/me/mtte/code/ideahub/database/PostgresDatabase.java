package me.mtte.code.ideahub.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

/**
 * Database implementation for Postgres
 */
public class PostgresDatabase implements Database {

    private final DSLContext dsl;

    public PostgresDatabase() {
        this.dsl = init();
    }

    @Override
    public DSLContext get() {
        return this.dsl;
    }

    /**
     * Setup the DSL context for jOOQ
     * @return jOOQ DSL context to access the db
     */
    private DSLContext init() {
        HikariConfig config = new HikariConfig();
        config.setDataSourceClassName("org.postgresql.ds.PGSimpleDataSource");
        config.setUsername(System.getenv("POSTGRES_USER"));
        config.setPassword(System.getenv("POSTGRES_PASSWORD"));
        config.addDataSourceProperty("databaseName", System.getenv("POSTGRES_DB"));
        config.addDataSourceProperty("serverName", System.getenv("POSTGRES_HOST"));
        config.addDataSourceProperty("portNumber", System.getenv("POSTGRES_PORT"));
        config.addDataSourceProperty("currentSchema", System.getenv("POSTGRES_DB"));

        HikariDataSource dataSource = new HikariDataSource(config);
        return DSL.using(dataSource, SQLDialect.POSTGRES);
    }

}
