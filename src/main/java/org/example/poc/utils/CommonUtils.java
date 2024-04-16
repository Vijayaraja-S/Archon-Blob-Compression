package org.example.poc.utils;

import org.example.poc.bean.CompressionTypes;
import org.example.poc.bean.StackBean;

import java.io.File;
import java.text.DecimalFormat;

public class CommonUtils {
    public CommonUtils() {}

    private static double getSeconds(long nanoSeconds) {
        return  (double) nanoSeconds/1_000_000_000;
    }

    public StackBean getStackBean(String inputPath, String outputPath, long startTime, CompressionTypes c) {
        DecimalFormat decimalFormat = new DecimalFormat("#.###");
        final String actualSize = decimalFormat.format(new File(inputPath).length() / 1024.0);
        final String processedSize = decimalFormat.format(new File(outputPath).length() / 1024.0);
        return  StackBean.builder()
                .actualPath(inputPath)
                .actualSize(actualSize)
                .processedPath(outputPath)
                .processedSize(processedSize)
                .compressionType(String.valueOf(c))
                .timeTaken(Double.parseDouble(decimalFormat.
                        format(getSeconds(System.nanoTime() -startTime))))
                .compressionRatio(Double.parseDouble(decimalFormat.
                        format(getCompressionRatio(processedSize,actualSize)))).build();
    }

    private static double getCompressionRatio(String compressedFileSize, String originalFileSize) {
        double originalSizeKB = Double.parseDouble(originalFileSize);
        double compressedSizeKB = Double.parseDouble(compressedFileSize);
        return originalSizeKB / compressedSizeKB;
    }

}
