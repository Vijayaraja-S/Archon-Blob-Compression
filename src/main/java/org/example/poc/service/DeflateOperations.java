package org.example.poc.service;

import org.example.poc.bean.StackBean;
import org.example.poc.utils.CommonUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class DeflateOperations implements CompressionHandler{
    private final CommonUtils commonUtils = new CommonUtils();
    @Override
    public StackBean compress(String inputPath, String outputPath) throws IOException {
        long startTime = System.nanoTime();
        byte[] input = Files.readAllBytes(Path.of(inputPath));
        Deflater deflater = new Deflater();
        deflater.setInput(input);
        deflater.finish();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int compressedSize = deflater.deflate(buffer);
            outputStream.write(buffer, 0, compressedSize);
        }
        return commonUtils.getStackBean(inputPath,outputPath,startTime);
    }

    @Override
    public StackBean deCompress(String inputPath, String outputPath) throws IOException, DataFormatException {
        long startTime = System.nanoTime();
        Inflater inflater = new Inflater();
        byte[] input = Files.readAllBytes(Path.of(inputPath));
        inflater.setInput(input);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];

        while (!inflater.finished()) {
            int decompressedSize = inflater.inflate(buffer);
            outputStream.write(buffer, 0, decompressedSize);
        }
        return commonUtils.getStackBean(inputPath,outputPath,startTime);
    }
}
