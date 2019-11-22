package com.validator.bpm.demo;

import java.util.Objects;

public class Demo {

    private String url;
    private String token;

    public Demo(String url, String token) {
        this.url = url;
        this.token = token;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Demo)) return false;
        Demo demo = (Demo) o;
        return getUrl().equals(demo.getUrl()) &&
                getToken().equals(demo.getToken());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUrl(), getToken());
    }
}
