package me.mtte.code.ideahub.auth;

import java.util.EnumSet;

/**
 * Defines all roles.
 */
public enum Role {
    USER,
    AUTHOR,
    ADMIN;

    /**
     * Get a role by it's name.
     * @param name The name of the role.
     * @return The Role itself or null if there is no role existing with that name.
     */
    public static Role getByName(String name) {
        for (Role role : EnumSet.allOf(Role.class)) {
            if (role.name().equals(name)) {
                return role;
            }
        }

        return null;
    }

    /**
     * Check if a role with the given name exists.
     * @param roleName The name to check.
     * @return If the role exists.
     */
    public static boolean validRole(String roleName) {
        var role = getByName(roleName);
        return role != null;
    }

}
