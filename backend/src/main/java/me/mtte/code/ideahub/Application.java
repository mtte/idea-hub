package me.mtte.code.ideahub;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import static spark.Spark.*;

public class Application {

    private static DSLContext dsl;

    public static void main(String[] args) {
        setupDatabase();
        setupApi();
    }

    private static void setupDatabase() {
        HikariConfig config = new HikariConfig();
        config.setDataSourceClassName("org.postgresql.ds.PGSimpleDataSource");
        config.setUsername(System.getenv("POSTGRES_USER"));
        config.setPassword(System.getenv("POSTGRES_PASSWORD"));
        config.addDataSourceProperty("databaseName", System.getenv("POSTGRES_DB"));
        config.addDataSourceProperty("serverName", "localhost");
        config.addDataSourceProperty("portNumber", "5432");
        config.addDataSourceProperty("currentSchema", System.getenv("POSTGRES_DB"));

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
