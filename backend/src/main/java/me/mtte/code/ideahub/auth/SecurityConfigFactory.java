package me.mtte.code.ideahub.auth;

import org.pac4j.core.authorization.authorizer.RequireAnyRoleAuthorizer;
import org.pac4j.core.config.Config;
import org.pac4j.core.config.ConfigFactory;
import org.pac4j.http.client.direct.HeaderClient;
import org.pac4j.jwt.config.signature.SecretSignatureConfiguration;
import org.pac4j.jwt.credentials.authenticator.JwtAuthenticator;
import org.pac4j.sparkjava.DefaultHttpActionAdapter;

import java.util.EnumSet;

/**
 * Factory to build the configuration for pac4j that is used by the {@link org.pac4j.sparkjava.SecurityFilter}.
 */
public class SecurityConfigFactory implements ConfigFactory {

    private final String jwtSalt;

    public SecurityConfigFactory(String jwtSalt) {
        this.jwtSalt = jwtSalt;
    }

    @Override
    public Config build(Object... parameters) {
        JwtAuthenticator jwtAuth = new JwtAuthenticator(new SecretSignatureConfiguration(this.jwtSalt));
        HeaderClient headerClient = new HeaderClient("Authorization", "Bearer", jwtAuth);
        Config config = new Config(headerClient);
        config.setHttpActionAdapter(new DefaultHttpActionAdapter());

        // Add all roles
        for (Role role : EnumSet.allOf(Role.class)) {
            config.addAuthorizer(role.toString(), new RequireAnyRoleAuthorizer<>(role.toString()));
        }

        return config;
    }

}
