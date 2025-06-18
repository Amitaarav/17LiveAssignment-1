package com.seventeenlive.writer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Writer for JSON output files.
 * Follows Single Responsibility Principle - only handles JSON writing.
 */
public class JsonOutputWriter {
    
    private static final Logger logger = LoggerFactory.getLogger(JsonOutputWriter.class);
    private final ObjectMapper objectMapper;
    
    public JsonOutputWriter() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }
    
    /**
     * Writes the result to a JSON file
     */
    public void writeToFile(Map<String, List<String>> result, String filePath) throws IOException {
        if (result == null) {
            throw new IllegalArgumentException("Result cannot be null");
        }
        
        if (filePath == null || filePath.trim().isEmpty()) {
            throw new IllegalArgumentException("File path cannot be null or empty");
        }
        
        logger.info("Writing result to file: {}", filePath);
        
        try {
            File file = new File(filePath);
            // Create parent directories if they don't exist
            File parentDir = file.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                boolean created = parentDir.mkdirs();
                if (!created) {
                    logger.warn("Could not create parent directories for: {}", filePath);
                }
            }
            
            objectMapper.writeValue(file, result);
            logger.info("Successfully wrote result to file: {}", filePath);
        } catch (IOException e) {
            logger.error("Failed to write result to file: {}", filePath, e);
            throw new IOException("Failed to write result to file: " + e.getMessage(), e);
        }
    }
    
    /**
     * Converts the result to JSON string
     */
    public String writeToString(Map<String, List<String>> result) throws IOException {
        if (result == null) {
            throw new IllegalArgumentException("Result cannot be null");
        }
        
        try {
            String jsonString = objectMapper.writeValueAsString(result);
            logger.debug("Successfully converted result to JSON string of length: {}", jsonString.length());
            return jsonString;
        } catch (IOException e) {
            logger.error("Failed to convert result to JSON string", e);
            throw new IOException("Failed to convert result to JSON string: " + e.getMessage(), e);
        }
    }
}