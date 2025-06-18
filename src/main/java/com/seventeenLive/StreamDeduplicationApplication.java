package com.seventeenlive;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.seventeenlive.factory.DeduplicationStrategyFactory;
import com.seventeenlive.model.Section;
import com.seventeenlive.parser.JsonDataParser;
import com.seventeenlive.service.StreamDeduplicationService;
import com.seventeenlive.strategy.DeduplicationStrategy;
import com.seventeenlive.writer.JsonOutputWriter;

/**
 * Main application class for Stream Deduplication.
 * Follows Single Responsibility Principle and demonstrates Dependency Injection.
 */
public class StreamDeduplicationApplication {
    
    private static final Logger logger = LoggerFactory.getLogger(StreamDeduplicationApplication.class);
    
    private final JsonDataParser parser;
    private final JsonOutputWriter writer;
    private final StreamDeduplicationService service;
    
    public StreamDeduplicationApplication() {
        this.parser = new JsonDataParser();
        this.writer = new JsonOutputWriter();
        
        // Use factory to create strategy (Dependency Injection)
        DeduplicationStrategy strategy = DeduplicationStrategyFactory.createDefaultStrategy();
        this.service = new StreamDeduplicationService(strategy);
    }
    
    /**
     * Processes the input file and generates output file
     */
    public void processFile(String inputFilePath, String outputFilePath) {
        try {
            logger.info("Starting stream deduplication process");
            logger.info("Input file: {}", inputFilePath);
            logger.info("Output file: {}", outputFilePath);
            
            // Parse input
            List<Section> sections = parser.parseFromFile(inputFilePath);
            logger.info("Loaded {} sections from input file", sections.size());
            
            // Process streams
            Map<String, List<String>> result = service.processStreams(sections);
            
            // Write output
            writer.writeToFile(result, outputFilePath);
            
            logger.info("Stream deduplication completed successfully");
            
        } catch (IOException e) {
            logger.error("IO error during processing", e);
            throw new RuntimeException("IO error: " + e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Unexpected error during processing", e);
            throw new RuntimeException("Processing failed: " + e.getMessage(), e);
        }
    }
    
    /**
     * Main method - entry point of the application
     */
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Usage: java -jar stream-deduplication.jar <input-file> <output-file>");
            System.err.println("Example: java -jar stream-deduplication.jar input.json output.json");
            System.exit(1);
        }
        
        String inputFile = args[0];
        String outputFile = args[1];
        
        try {
            StreamDeduplicationApplication app = new StreamDeduplicationApplication();
            app.processFile(inputFile, outputFile);
            System.out.println("Processing completed successfully!");
            System.out.println("Output written to: " + outputFile);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            logger.error("Application failed", e);
            System.exit(1);
        }
    }
}