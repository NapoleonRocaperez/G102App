package com.example.g102app.models;

public class Pokemon {
    private int number;
    private String name;
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getNumber() {
        String[] urlPartes = url.split("/");//separar la parte de la url
        return Integer.parseInt(urlPartes[urlPartes.length - 1]);
    }//y en la ultima posicion esta el numero

    public void setNumber(int number) {
        this.number = number;
    }
}
