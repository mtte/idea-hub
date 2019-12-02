package me.mtte.code.ideahub;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import static me.mtte.code.ideahub.database.ideahub.tables.Category.CATEGORY;
import static spark.Spark.*;

public class Application {

    private static DSLContext dsl;

    public static void main(String[] args) {
        setupDatabase();
        setupApi();
    }

    private static void setupDatabase() {
        // TODO: Do not use hardcoded values
        HikariConfig config = new HikariConfig();
        config.setDataSourceClassName("org.postgresql.ds.PGSimpleDataSource");
        config.setUsername("ideahub");
        config.setPassword("pass4dev");
        config.addDataSourceProperty("databaseName", "ideahub");
        config.addDataSourceProperty("serverName", "localhost");
        config.addDataSourceProperty("portNumber", "5432");
        config.addDataSourceProperty("currentSchema", "ideahub");

        HikariDataSource dataSource = new HikariDataSource(config);
        dsl = DSL.using(dataSource, SQLDialect.POSTGRES);
    }

    private static void setupApi() {
        port(80);

        path("v1",() -> {
            get("/", (req, res) -> "Hello World!");
        });

        after((req, res) -> {
            res.header("Content-Encoding", "gzip");
            res.type("application/json");
        });
    }

}
