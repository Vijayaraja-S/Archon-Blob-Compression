package org.example.poc.service;

import org.example.poc.bean.CompressionTypes;

public class CompressionEngine {
    private final CompressionTypes types;
    private CompressionHandler ch;


    public CompressionEngine(CompressionTypes types) {
        this.types = types;
    }

    public void initialize() throws Exception {
        this.ch = this.getCompressionEngine();
    }

    public CompressionHandler getCompressionEngine(){
        switch (types){
            case GZIP -> {
                return new GZIPOperations();
            }
        }

        return null;
    }
}
