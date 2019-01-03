package ru.skogmark.valhall.core.content.parser;

import ru.skogmark.valhall.core.content.Content;

public interface Parser {
    void auth(ParserAuthorizationListener listener);

    Content parse(long offset);
}
