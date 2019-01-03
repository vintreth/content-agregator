package ru.skogmark.valhall.core.content.source;

import javax.annotation.Nonnull;

import static java.util.Objects.requireNonNull;

public class AuthorizationMeta {
    @Nonnull
    private final Source source;

    @Nonnull
    private final String authorizationToken;

    private AuthorizationMeta(@Nonnull Source source, @Nonnull String authorizationToken) {
        this.source = requireNonNull(source, "source");
        this.authorizationToken = requireNonNull(authorizationToken, "authorizationToken");
    }

    public static AuthorizationMeta of(@Nonnull Source source, @Nonnull String authorizationToken) {
        return new AuthorizationMeta(source, authorizationToken);
    }

    @Nonnull
    public Source getSource() {
        return source;
    }

    @Nonnull
    public String getAuthorizationToken() {
        return authorizationToken;
    }

    @Override
    public String toString() {
        return "AuthorizationMeta{" +
                "source=" + source +
                ", authorizationToken='" + authorizationToken + '\'' +
                '}';
    }
}
