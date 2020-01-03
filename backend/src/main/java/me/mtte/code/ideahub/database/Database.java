package me.mtte.code.ideahub.database;

import org.jooq.DSLContext;

public interface Database {

    /**
     * Get the dsl context for the database.
     * @return The {@link DSLContext}.
     */
    DSLContext get();

}
