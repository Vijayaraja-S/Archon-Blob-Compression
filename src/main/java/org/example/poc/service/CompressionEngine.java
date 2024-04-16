package org.example.poc.service;

import org.example.poc.bean.CompressionTypes;


public class CompressionEngine {
    private final CompressionTypes types;
    private CompressionHandler ch;


    public CompressionEngine(CompressionTypes types) {
        this.types = types;
    }

    public CompressionHandler inti(){
        this.ch = this.getCompressionEngine();
        return ch;
    }

    public CompressionHandler getCompressionEngine(){
        switch (types){
            case GZIP :
                return new GZIPOperations();
            case DEFLATE:
                return new DeflateOperations();
        }

        return null;
    }

//    public StackBean compress(String path, String outputPath) throws IOException {
//        return ch.compress(path,outputPath);
//    }
//
//    public StackBean deCompress(String processedPath, String inputPath) throws IOException, DataFormatException {
//        return ch.deCompress(processedPath,inputPath);
//    }
}
