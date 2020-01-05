package me.mtte.code.ideahub.model;

import me.mtte.code.ideahub.auth.Role;
import me.mtte.code.ideahub.database.ideahub.tables.records.UserRecord;
import org.pac4j.core.profile.CommonProfile;

public class User {

    private final int id;
    private final String username;
    private final Role role;
    private String password;

    public User(UserRecord userRecord) {
        this(userRecord.getId(), userRecord.getName(), Role.getByName(userRecord.getRole()));
    }

    public User(int id, String username, Role role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }

    public int getId() {
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }

    public Role getRole() {
        return this.role;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
