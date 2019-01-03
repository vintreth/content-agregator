package ru.skogmark.valhall.core.content.parser;

import ru.skogmark.valhall.core.content.AuthorizationMeta;

@FunctionalInterface
public interface ParserAuthorizationListener {
    void onAuthorizationComplete(AuthorizationMeta authorizationMeta);
}
