package org.example.poc.service;

import org.example.poc.bean.StackBean;

import java.io.IOException;

public interface CompressionHandler {
    StackBean compress(String inputPath, String outputPath) throws IOException;

    StackBean deCompress(String inputPath, String outputPath) throws IOException;
}
