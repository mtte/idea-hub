package me.mtte.code.ideahub;

import static spark.Spark.*;

public class Application {

    public static void main(String[] args) {
        port(80);

        get("/", (req, res) -> "Hello World!");

        after((req, res) -> res.header("Content-Encoding", "gzip"));
    }

}
