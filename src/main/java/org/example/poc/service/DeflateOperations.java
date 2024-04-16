package org.example.poc.service;

import org.apache.commons.io.FilenameUtils;
import org.example.poc.bean.CompressionTypes;
import org.example.poc.bean.StackBean;
import org.example.poc.utils.CommonUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class DeflateOperations implements CompressionHandler{
    private final CommonUtils commonUtils = new CommonUtils();

    public DeflateOperations() {
    }

    @Override
    public StackBean compress(String inputPath, String outputPath) throws IOException {
        try {
            long startTime = System.nanoTime();
            String compressedFilePath = outputPath + File.separator + FilenameUtils.removeExtension(new File(inputPath).getName()) + ".dfl";
            byte[] input = Files.readAllBytes(Path.of(inputPath));
            Deflater deflater = new Deflater(Deflater.HUFFMAN_ONLY);
            deflater.setInput(input);
            deflater.finish();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            while (!deflater.finished()) {
                int compressedSize = deflater.deflate(buffer);
                outputStream.write(buffer, 0, compressedSize);
            }
            byte[] compressedData = outputStream.toByteArray();
            Files.write(Path.of(compressedFilePath), compressedData);
            return commonUtils.getStackBean(inputPath, compressedFilePath, startTime, CompressionTypes.DEFLATE);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public StackBean deCompress(String inputPath, String outputPath) throws DataFormatException, IOException {
        try {
            long startTime = System.nanoTime();
            final File directory = new File(outputPath + File.separator + "DECOMPRESS");
            if (!directory.exists()) {
                directory.mkdir();
            }
            Inflater inflater = new Inflater();
            byte[] input = Files.readAllBytes(Path.of(inputPath));
            inflater.setInput(input);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];

            while (!inflater.finished()) {
                int decompressedSize = inflater.inflate(buffer);
                outputStream.write(buffer, 0, decompressedSize);
            }

            String decompressedFilePath = outputPath + File.separator + "DECOMPRESS" + File.separator +
                    FilenameUtils.removeExtension(new File(inputPath).getName());
            Files.write(Path.of(decompressedFilePath), outputStream.toByteArray());
            return commonUtils.getStackBean(inputPath, decompressedFilePath, startTime, CompressionTypes.DEFLATE);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
