package com.seventeenlive.strategy;

import java.util.List;
import java.util.Map;

import com.seventeenlive.model.Section;

public interface DeduplicationStrategy {
    
    /**
     * @param sections List of sections to process
     * @param topPositions Number of top positions to check for duplicates
     * @return Map of section ID to deduplicated streamer list
     */
    Map<String, List<String>> deduplicate(List<Section> sections, int topPositions);
}