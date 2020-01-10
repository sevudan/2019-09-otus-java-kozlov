package ru.otus.hw11.cache;

public enum CacheAction {

    ADD_INTO_CACHE("ADD_INTO_CACHE"),
    REMOVE_FROM_CACHE("REMOVE_FROM_CACHE");

    private String action;

    CacheAction(String envUrl) {
        this.action = envUrl;
    }

    public String getAction() {
        return action;
    }

}
