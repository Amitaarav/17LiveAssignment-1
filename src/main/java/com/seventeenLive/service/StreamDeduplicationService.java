package com.seventeenlive.service;

import com.seventeenlive.model.Section;
import com.seventeenlive.strategy.DeduplicationStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * Service class for stream deduplication operations.
 * Follows Single Responsibility Principle and Dependency Inversion Principle.
 */
public class StreamDeduplicationService {
    
    private static final Logger logger = LoggerFactory.getLogger(StreamDeduplicationService.class);
    private final DeduplicationStrategy strategy;
    
    public StreamDeduplicationService(DeduplicationStrategy strategy) {
        if (strategy == null) {
            throw new IllegalArgumentException("Deduplication strategy cannot be null");
        }
        this.strategy = strategy;
    }
    
    /**
     * Processes sections to remove duplicate streamers from top positions
     * 
     * @param sections List of sections to process
     * @param topPositions Number of top positions to deduplicate (default: 3)
     * @return Map of section ID to processed streamer list
     */
    public Map<String, List<String>> processStreams(List<Section> sections, int topPositions) {
        logger.info("Processing {} sections for stream deduplication", 
                   sections != null ? sections.size() : 0);
        
        validateInput(sections, topPositions);
        
        try {
            Map<String, List<String>> result = strategy.deduplicate(sections, topPositions);
            logger.info("Successfully processed streams. Result contains {} sections", result.size());
            return result;
        } catch (Exception e) {
            logger.error("Error during stream processing", e);
            throw new RuntimeException("Failed to process streams: " + e.getMessage(), e);
        }
    }
    
    /**
     * Processes sections with default top 3 positions
     */
    public Map<String, List<String>> processStreams(List<Section> sections) {
        return processStreams(sections, 3);
    }
    
    /**
     * Validates input parameters
     */
    private void validateInput(List<Section> sections, int topPositions) {
        if (sections == null) {
            throw new IllegalArgumentException("Sections list cannot be null");
        }
        
        if (topPositions <= 0) {
            throw new IllegalArgumentException("Top positions must be greater than 0");
        }
        
        if (topPositions > 100) {
            logger.warn("Top positions value {} seems unusually high", topPositions);
        }
    }
}