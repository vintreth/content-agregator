package ru.skogmark.valhall.core.content.parser;

import ru.skogmark.valhall.core.content.source.AuthorizationMeta;

public interface ParserAuthorizationListener {
    void onAuthorizationComplete(AuthorizationMeta authorizationMeta);
}
