package me.mtte.code.ideahub.responses;

import me.mtte.code.ideahub.auth.Role;

public class LoginResponse {

    private final String token;
    private final String username;
    private final Role role;

    public LoginResponse(String token, String username, Role role) {
        this.token = token;
        this.username = username;
        this.role = role;
    }

}
