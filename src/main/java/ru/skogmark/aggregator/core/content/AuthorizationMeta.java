package ru.skogmark.aggregator.core.content;

import javax.annotation.Nonnull;

import static java.util.Objects.requireNonNull;

public class AuthorizationMeta {
    @Nonnull
    private final int sourceId;

    @Nonnull
    private final String authorizationToken;

    private AuthorizationMeta(int sourceId, @Nonnull String authorizationToken) {
        this.sourceId = sourceId;
        this.authorizationToken = requireNonNull(authorizationToken, "authorizationToken");
    }

    static AuthorizationMeta of(int sourceId, @Nonnull String authorizationToken) {
        return new AuthorizationMeta(sourceId, authorizationToken);
    }

    int getSourceId() {
        return sourceId;
    }

    @Nonnull
    String getAuthorizationToken() {
        return authorizationToken;
    }

    @Override
    public String toString() {
        return "AuthorizationMeta{" +
                "sourceId=" + sourceId +
                ", authorizationToken='" + authorizationToken + '\'' +
                '}';
    }
}
