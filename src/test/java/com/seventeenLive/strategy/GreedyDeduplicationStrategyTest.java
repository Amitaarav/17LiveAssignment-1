package com.seventeenlive.strategy;

import com.seventeenlive.model.Section;
import com.seventeenlive.model.StreamerData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for GreedyDeduplicationStrategy
 */
class GreedyDeduplicationStrategyTest{
    
    private GreedyDeduplicationStrategy strategy;
    
    @BeforeEach
    void setUp() {
        strategy = new GreedyDeduplicationStrategy();
    }
    
    @Test
    void testDeduplicateWithNormalCase() {
        // Arrange
        List<Section> sections = createTestSections();
        
        // Act
        Map<String, List<String>> result = strategy.deduplicate(sections, 3);
        
        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        
        List<String> section1 = result.get("Section1");
        List<String> section2 = result.get("Section2");
        
        assertNotNull(section1);
        assertNotNull(section2);
        
        // Check that top 3 positions don't have duplicates across sections
        Set<String> topStreamersSection1 = new HashSet<>(section1.subList(0, Math.min(3, section1.size())));
        Set<String> topStreamersSection2 = new HashSet<>(section2.subList(0, Math.min(3, section2.size())));
        
        // No intersection in top 3
        topStreamersSection1.retainAll(topStreamersSection2);
        assertTrue(topStreamersSection1.isEmpty(), "Top 3 positions should not have duplicates");
    }
    
    @Test
    void testDeduplicateWithEmptyList() {
        // Act
        Map<String, List<String>> result = strategy.deduplicate(new ArrayList<>(), 3);
        
        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    void testDeduplicateWithNullList() {
        // Act
        Map<String, List<String>> result = strategy.deduplicate(null, 3);
        
        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    void testDeduplicateWithInvalidTopPositions() {
        // Arrange
        List<Section> sections = createTestSections();
        
        // Act
        Map<String, List<String>> result = strategy.deduplicate(sections, 0);
        
        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    void testDeduplicateWithSingleSection() {
        // Arrange
        List<Section> sections = Arrays.asList(createSection("Section1", 
            Arrays.asList("streamer1", "streamer2", "streamer3")));
        
        // Act
        Map<String, List<String>> result = strategy.deduplicate(sections, 3);
        
        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        
        List<String> section1 = result.get("Section1");
        assertEquals(3, section1.size());
        assertEquals("streamer1", section1.get(0));
        assertEquals("streamer2", section1.get(1));
        assertEquals("streamer3", section1.get(2));
    }
    
    private List<Section> createTestSections() {
        return Arrays.asList(
            createSection("Section1", Arrays.asList("streamer1", "streamer2", "streamer3", "streamer4")),
            createSection("Section2", Arrays.asList("streamer2", "streamer3", "streamer5", "streamer6"))
        );
    }
    
    private Section createSection(String sectionId, List<String> streamerIds) {
        List<StreamerData> streamers = new ArrayList<>();
        for (String id : streamerIds) {
            streamers.add(new StreamerData(id));
        }
        return new Section(sectionId, streamers);
    }
}