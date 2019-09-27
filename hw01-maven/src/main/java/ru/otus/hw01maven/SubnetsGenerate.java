package ru.otus.hw01maven;

import java.util.HashMap;
import java.util.Map;

abstract class AbstractSubnetsGenerate {
    
    static Map<String, Subnet> getUsedSites() {

        Map<String, Subnet> usedSites = new HashMap<>();

        usedSites.put("213.180.193.1", new Subnet("213.180.193.1","yandex.ru"));
        usedSites.put("195.82.146.214", new Subnet("195.82.146.214","rutracker.org"));
        usedSites.put("178.248.237.15", new Subnet("178.248.237.15","d3.ru"));
        usedSites.put("108.174.10.10", new Subnet("108.174.10.10","linkedin.com"));
        usedSites.put("178.248.237.68", new Subnet("178.248.237.68","habr.com"));
        usedSites.put("149.154.167.99", new Subnet("149.154.167.99", "telegram.org"));

        return usedSites;
    }

    static Map<String, Subnet> getBlackList() {

        Map<String, Subnet> blackList = new HashMap<>();

        blackList.put("149.154.167.99", new Subnet("149.154.167.99", "telegram.org"));
        blackList.put("195.82.146.214", new Subnet("195.82.146.214", "rutracker.org"));
        blackList.put("108.174.10.10",new Subnet("108.174.10.10", "linkedin.com"));
        blackList.put("91.132.60.14", new Subnet("91.132.60.14","rutor.info"));
        blackList.put("93.190.137.161", new Subnet("93.190.137.161","lib.rus.ec"));
        blackList.put("145.239.118.84", new Subnet("145.239.118.84", "lib.rus.ec"));

        return blackList;
    }
}