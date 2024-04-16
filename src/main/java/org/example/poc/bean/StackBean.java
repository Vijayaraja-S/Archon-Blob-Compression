package org.example.poc.bean;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Getter
@Setter
public class StackBean {
    private String actualPath;
    private String processedPath;
    private String actualSize;
    private String processedSize;
    private String compressionType;
    private double timeTaken;
    private double compressionRatio;
}
