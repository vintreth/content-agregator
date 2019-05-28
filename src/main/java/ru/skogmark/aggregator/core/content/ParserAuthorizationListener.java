package ru.skogmark.aggregator.core.content;

@FunctionalInterface
public interface ParserAuthorizationListener {
    void onAuthorizationComplete(AuthorizationMeta authorizationMeta);
}
