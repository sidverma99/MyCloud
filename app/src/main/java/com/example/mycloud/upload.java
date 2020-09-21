package com.example.mycloud;

public class upload {
    private String name;
    private String url;

    public upload() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public upload(String name, String url) {
        this.name = name;
        this.url = url;
    }
}
