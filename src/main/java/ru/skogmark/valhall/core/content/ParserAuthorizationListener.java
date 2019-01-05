package ru.skogmark.valhall.core.content;

@FunctionalInterface
public interface ParserAuthorizationListener {
    void onAuthorizationComplete(AuthorizationMeta authorizationMeta);
}
