package org.example.poc.service;

import org.apache.commons.io.FilenameUtils;
import org.example.poc.bean.StackBean;
import org.example.poc.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;


public class    GZIPOperations implements CompressionHandler {

    private final CommonUtils commonUtils = new CommonUtils();

    public GZIPOperations() { /* TODO document why this constructor is empty */ }

    @Override
    public StackBean compress(String inputPath, String outputPath) throws IOException {
        long startTime = System.nanoTime();
        outputPath=outputPath+File.separator+ FilenameUtils.getExtension(new File(inputPath).getName())+".gz";
        byte[] buffer = new byte[1024];
        try {
            GZIPOutputStream os = new GZIPOutputStream(new FileOutputStream(outputPath));
            FileInputStream in = new FileInputStream(inputPath);
            int totalSize;
            while((totalSize = in.read(buffer)) > 0 ) {
                os.write(buffer, 0, totalSize);
            }
            in.close();
            os.finish();
            os.close();
            return commonUtils.getStackBean(inputPath,outputPath,startTime);
        }
        catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }


    @Override
    public StackBean deCompress(String inputPath, String outputPath) throws IOException {
        long startTime = System.nanoTime();
        byte[] buffer = new byte[1024];
        final File directory = new File(outputPath + File.separator + "DECOMPRESS");
        if (!directory.exists()) {
            directory.mkdir();
        }
        outputPath = directory.getAbsoluteFile() + File.separator + "de_compressed" +"_"+ new File(inputPath).getName();
        try {
            GZIPInputStream is = new GZIPInputStream(new FileInputStream(inputPath));
            FileOutputStream out =new FileOutputStream(outputPath);
            int totalSize;
            while((totalSize = is.read(buffer)) > 0 ) {
                out.write(buffer, 0, totalSize);
            }
            out.close();
            is.close();
            return commonUtils.getStackBean(inputPath,outputPath,startTime);
        }
        catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }



}
