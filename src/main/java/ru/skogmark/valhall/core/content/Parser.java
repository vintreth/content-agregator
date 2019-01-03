package ru.skogmark.valhall.core.content;

public interface Parser {
    void auth(ParserAuthorizationListener listener);

    Content parse(long offset);
}
