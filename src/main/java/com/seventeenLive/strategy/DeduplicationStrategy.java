package com.seventeenlive.strategy;

import com.seventeenlive.model.Section;
import java.util.List;
import java.util.Map;

/**
 * Strategy interface for different deduplication algorithms.
 * Follows Strategy Pattern and Interface Segregation Principle.
 */
public interface DeduplicationStrategy {
    
    /**
     * Removes duplicates from the top positions of sections
     * 
     * @param sections List of sections to process
     * @param topPositions Number of top positions to check for duplicates
     * @return Map of section ID to deduplicated streamer list
     */
    Map<String, List<String>> deduplicate(List<Section> sections, int topPositions);
}