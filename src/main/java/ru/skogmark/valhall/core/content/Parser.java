package ru.skogmark.valhall.core.content;

public interface Parser {
    AuthorizationMeta auth();
    Content parse(long offset);
}
