package com.seventeenlive.strategy;

import com.seventeenlive.model.Section;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Greedy deduplication strategy that processes sections in order
 * and replaces duplicates in top positions with available alternatives.
 * Follows Strategy Pattern and Single Responsibility Principle.
 */
public class GreedyDeduplicationStrategy implements DeduplicationStrategy {
    
    private static final Logger logger = LoggerFactory.getLogger(GreedyDeduplicationStrategy.class);
    
    @Override
    public Map<String, List<String>> deduplicate(List<Section> sections, int topPositions) {
        if (sections == null || sections.isEmpty()) {
            logger.warn("Empty or null sections provided");
            return new HashMap<>();
        }
        
        if (topPositions <= 0) {
            logger.warn("Invalid topPositions value: {}", topPositions);
            return new HashMap<>();
        }
        
        Map<String, List<String>> result = new LinkedHashMap<>();
        Set<String> usedStreamers = new HashSet<>();
        
        logger.info("Starting deduplication for {} sections with {} top positions", 
                   sections.size(), topPositions);
        
        for (Section section : sections) {
            if (section == null || section.getSectionId() == null) {
                logger.warn("Skipping null section or section with null ID");
                continue;
            }
            
            List<String> deduplicatedStreamers = processSection(section, topPositions, usedStreamers);
            result.put(section.getSectionId(), deduplicatedStreamers);
            
            logger.debug("Processed section '{}' with {} streamers", 
                        section.getSectionId(), deduplicatedStreamers.size());
        }
        
        logger.info("Deduplication completed for {} sections", result.size());
        return result;
    }
    
    /**
     * Processes a single section to remove duplicates from top positions
     */
    private List<String> processSection(Section section, int topPositions, Set<String> usedStreamers) {
        List<String> allStreamers = section.getAllStreamerIds();
        List<String> result = new ArrayList<>();
        
        if (allStreamers.isEmpty()) {
            logger.warn("Section '{}' has no streamers", section.getSectionId());
            return result;
        }
        
        // Create a pool of available streamers (excluding already used ones)
        List<String> availableStreamers = new ArrayList<>();
        for (String streamerId : allStreamers) {
            if (streamerId != null && !usedStreamers.contains(streamerId)) {
                availableStreamers.add(streamerId);
            }
        }
        
        // Fill top positions with available streamers
        int positionsToFill = Math.min(topPositions, availableStreamers.size());
        for (int i = 0; i < positionsToFill; i++) {
            String streamerId = availableStreamers.get(i);
            result.add(streamerId);
            usedStreamers.add(streamerId);
        }
        
        // Add remaining streamers (including duplicates) after top positions
        for (int i = topPositions; i < allStreamers.size(); i++) {
            String streamerId = allStreamers.get(i);
            if (streamerId != null) {
                result.add(streamerId);
            }
        }
        
        // Handle edge case: if we couldn't fill all top positions
        if (result.size() < topPositions && result.size() < allStreamers.size()) {
            logger.warn("Could only fill {} out of {} top positions for section '{}'", 
                       positionsToFill, topPositions, section.getSectionId());
        }
        
        return result;
    }
}