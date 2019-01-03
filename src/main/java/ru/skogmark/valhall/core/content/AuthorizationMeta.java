package ru.skogmark.valhall.core.content;

import javax.annotation.Nonnull;

import static java.util.Objects.requireNonNull;

public class AuthorizationMeta {
    private final Source source;
    private final String authorizationToken;

    private AuthorizationMeta(@Nonnull Source source, @Nonnull String authorizationToken) {
        this.source = requireNonNull(source, "source");
        this.authorizationToken = requireNonNull(authorizationToken, "authorizationToken");
    }

    public static AuthorizationMeta of(@Nonnull Source source, @Nonnull String authorizationToken) {
        return new AuthorizationMeta(source, authorizationToken);
    }

    public Source getSource() {
        return source;
    }

    public String getAuthorizationToken() {
        return authorizationToken;
    }
}
