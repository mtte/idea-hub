package me.mtte.code.ideahub.service;

import me.mtte.code.ideahub.database.Database;
import me.mtte.code.ideahub.database.ideahub.tables.records.UserRecord;
import me.mtte.code.ideahub.model.User;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static me.mtte.code.ideahub.database.ideahub.tables.User.USER;

public class UserService extends AbstractService {

    public UserService(Database database) {
        super(database);
    }

    public Optional<User> getUser(int id) {
        var user = getDb().fetchOptional(USER, USER.ID.eq(id));
        return user.map(User::new);
    }

    public Optional<User> getByUsername(String username) {
        var user = getDb().fetchOptional(USER, USER.NAME.eq(username));
        return user.map(User::new);
    }

    public Optional<User> getByUsernameWithPassword(String username) {
        var userRecord = getDb().fetchAny(USER, USER.NAME.eq(username));
        if (userRecord == null) {
            return Optional.empty();
        }
        var user = new User(userRecord);
        user.setPassword(userRecord.getPassword());
        user.setTwoFactorAuthSecret(userRecord.getTwoFactorAuth());
        return Optional.of(user);
    }

    public Set<User> getAllUsers() {
        var result = getDb().selectFrom(USER).fetch();
        return result.stream().map(User::new).collect(Collectors.toSet());
    }

    public User createUser(String username, String passwordHash, String role) {
        UserRecord userRecord = getDb().newRecord(USER);
        userRecord.setName(username);
        userRecord.setPassword(passwordHash);
        userRecord.setRole(role);
        userRecord.store();

        return new User(userRecord);
    }

    public boolean deleteUser(int id) {
        int deleted = getDb().delete(USER)
                .where(USER.ID.eq(id))
                .execute();
        return deleted == 1;
    }

    public User updateUser(int id, String username, String role) {
        var user = getDb().fetchOne(USER, USER.ID.eq(id));

        if (user == null) {
            return null;
        }

        if (username != null) {
            user.setName(username);
        }
        if (role != null) {
            user.setRole(role);
        }

        user.store();

        return new User(user);
    }

    public boolean isUsernameUnique(String username) {
        return getDb().fetchCount(USER, USER.NAME.eq(username)) == 0;
    }

    public boolean enableTwoFactorAuth(int userId, String secret) {
        var user = getDb().fetchOne(USER, USER.ID.eq(userId));
        if (user == null) {
            return false;
        }
        user.setTwoFactorAuth(secret);
        return user.store() != 0;
    }

    public boolean disableTwoFactroAuth(int userId) {
        var user = getDb().fetchOne(USER, USER.ID.eq(userId));
        if (user == null) {
            return false;
        }
        user.setTwoFactorAuth(null);
        return user.store() != 0;
    }

    public boolean isTwoFactorAuthEnabled(int userId) {
        var user = getDb().fetchOne(USER, USER.ID.eq(userId));
        return  user == null || user.getTwoFactorAuth() == null;
    }

}
