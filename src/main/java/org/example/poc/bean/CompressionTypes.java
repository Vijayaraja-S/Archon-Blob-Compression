package org.example.poc.bean;

public enum CompressionTypes {
    GZIP("gzip"),
    DEFLATE("deflate");
    private final String value;

    CompressionTypes(String value) {
        this.value = value;

    }
    public String value() {
        return value;
    }
}
