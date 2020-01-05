package me.mtte.code.ideahub.service;

import me.mtte.code.ideahub.database.Database;
import org.jooq.DSLContext;

public abstract class AbstractService implements Service {

    private final Database database;

    public AbstractService(Database database) {
        this.database = database;
    }

    @Override
    public DSLContext getDb() {
        return this.database.get();
    }
}
