package org.example;

import com.p3.ads.export.formatter.ExportFormat;
import com.p3.ads.export.operation.ExportEngine;
import com.p3.ads.export.options.ColumnInfo;
import com.p3.ads.export.specifics.DataType;
import org.example.poc.bean.CompressionTypes;
import org.example.poc.bean.StackBean;
import org.example.poc.service.CompressionEngine;
import org.example.poc.service.CompressionHandler;

import java.io.File;
import java.util.*;

public class Main {

    static final String OUTPUT_PATH = "/home/vijayaraja/Music/Bloblist/sample/output";
    static final String INPUT_PATH = "/home/vijayaraja/Music/Bloblist/sample/blob";
    static final List<CompressionTypes> COMPRESSION_TYPES_LIST = Arrays.asList(CompressionTypes.values());

    public static void main(String[] args) throws Exception {
        startCompression();
    }

    private static ExportEngine getExportEngine() throws Exception {
        ExportEngine  ee = ExportEngine.builder()
                .exportFormat(ExportFormat.csv)
                .basePath("/home/vijayaraja/Music/Bloblist/sample/resultFolder")
                .title("sample-1")
                .columnsInfo(prepareColumnHeaders()).build();
        ee.initialize();
        ee.handleDataStart();
        return ee;
    }

    private static void startCompression() throws Exception {
        for (CompressionTypes c : COMPRESSION_TYPES_LIST) {
            final ExportEngine ee = getExportEngine();
            CompressionHandler ce = new CompressionEngine(c).getCompressionEngine();
            for (File file : Objects.requireNonNull(new File(INPUT_PATH).listFiles())) {
                if (!file.isDirectory()) {
                    StackBean cBean = ce.compress(file.getPath(), OUTPUT_PATH);
                    StackBean dBean = ce.deCompress(cBean.getProcessedPath(), INPUT_PATH);
                    ee.iterateRows(getResultRow(cBean,dBean,file,c));
                }
            }
            ee.handleDataEnd();
            ee.generateReport();
        }
    }

    private static List<Object> getResultRow(StackBean cBean, StackBean dBean, File file, CompressionTypes c) {
        List<Object> row = new LinkedList<>();
        row.add(file.getName());
        row.add(cBean.getActualSize());
        row.add(c);
        row.add(cBean.getCompressionRatio());
        row.add(cBean.getProcessedSize());
        row.add(cBean.getTimeTaken());
        row.add(dBean.getProcessedSize());
        row.add(dBean.getTimeTaken());
        return row;
    }

    private static List<ColumnInfo> prepareColumnHeaders() {
        List<ColumnInfo>columnInfos= new ArrayList<>();
        final ArrayList<String> colNames= new ArrayList<>();
        colNames.add("FileName");
        colNames.add("FileSize(KB)");
        colNames.add("Compression_type");
        colNames.add("Ratio_of_Compression");
        colNames.add("Compressed_File_Size(KB)");
        colNames.add("Time_Taken_for_Compression(SEC)");
        colNames.add("De-Compression_Size(KB)");
        colNames.add("Time_Taken_for_Decompression(SEC)");
        int ordinalPosition=1;
        for (String columnName : colNames) {
                columnInfos.add(ColumnInfo.builder()
                        .column(columnName)
                        .ordinalPosition(ordinalPosition)
                        .dataType(DataType.STRING)
                        .build());
                ordinalPosition++;
        }
        return columnInfos;
    }

}
