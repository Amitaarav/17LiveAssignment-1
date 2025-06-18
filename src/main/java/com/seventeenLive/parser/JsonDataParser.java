package com.seventeenlive.parser;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seventeenlive.model.Section;
import org.slf4j.*;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Parser for JSON data files.
 * Follows Single Responsibility Principle - only handles JSON parsing.
 */
public class JsonDataParser {
    
    private static final Logger logger = LoggerFactory.getLogger(JsonDataParser.class);
    private final ObjectMapper objectMapper;
    
    public JsonDataParser() {
        this.objectMapper = new ObjectMapper();
    }
    
    /**
     * Parses sections from JSON file
     */
    public List<Section> parseFromFile(String filePath) throws IOException {
        if (filePath == null || filePath.trim().isEmpty()) {
            throw new IllegalArgumentException("File path cannot be null or empty");
        }
        
        File file = new File(filePath);
        if (!file.exists()) {
            throw new IOException("File not found: " + filePath);
        }
        
        logger.info("Parsing JSON file: {}", filePath);
        
        try {
            List<Section> sections = objectMapper.readValue(file, new TypeReference<List<Section>>() {});
            logger.info("Successfully parsed {} sections from file", sections.size());
            return sections;
        } catch (IOException e) {
            logger.error("Failed to parse JSON file: {}", filePath, e);
            throw new IOException("Failed to parse JSON file: " + e.getMessage(), e);
        }
    }
    
    /**
     * Parses sections from JSON string
     */
    public List<Section> parseFromString(String jsonString) throws IOException {
        if (jsonString == null || jsonString.trim().isEmpty()) {
            throw new IllegalArgumentException("JSON string cannot be null or empty");
        }
        
        logger.debug("Parsing JSON string of length: {}", jsonString.length());
        
        try {
            List<Section> sections = objectMapper.readValue(jsonString, new TypeReference<List<Section>>() {});
            logger.info("Successfully parsed {} sections from string", sections.size());
            return sections;
        } catch (IOException e) {
            logger.error("Failed to parse JSON string", e);
            throw new IOException("Failed to parse JSON string: " + e.getMessage(), e);
        }
    }
    
    /**
     * Parses sections from InputStream
     */
    public List<Section> parseFromInputStream(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            throw new IllegalArgumentException("InputStream cannot be null");
        }
        
        logger.debug("Parsing JSON from InputStream");
        
        try {
            List<Section> sections = objectMapper.readValue(inputStream, new TypeReference<List<Section>>() {});
            logger.info("Successfully parsed {} sections from InputStream", sections.size());
            return sections;
        } catch (IOException e) {
            logger.error("Failed to parse JSON from InputStream", e);
            throw new IOException("Failed to parse JSON from InputStream: " + e.getMessage(), e);
        }
    }
}