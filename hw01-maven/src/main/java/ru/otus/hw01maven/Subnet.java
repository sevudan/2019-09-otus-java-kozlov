package ru.otus.hw01maven;

public class Subnet {

    private String domain;
    private String net;

    public Subnet(final String net,
                  final String domain) {

        this.net = net;
        this.domain = domain;

    }

    public String getDomain() {
        return domain;
    }

    public String getNet() {
        return net;
    }

    @Override
    public String toString() {
        return  domain + ":" + net;
    }
}
