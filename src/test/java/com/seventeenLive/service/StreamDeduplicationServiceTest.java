package com.seventeenlive.service;

import com.seventeenlive.model.Section;
import com.seventeenlive.strategy.DeduplicationStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for StreamDeduplicationService
 */
class StreamDeduplicationServiceTest {
    
    @Mock
    private DeduplicationStrategy mockStrategy;
    
    private StreamDeduplicationService service;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new StreamDeduplicationService(mockStrategy);
    }
    
    @Test
    void testConstructorWithNullStrategy() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new StreamDeduplicationService(null));
    }
    
    @Test
    void testProcessStreamsWithValidInput() {
        // Arrange
        List<Section> sections = Arrays.asList(new Section());
        Map<String, List<String>> expectedResult = new HashMap<>();
        when(mockStrategy.deduplicate(sections, 3)).thenReturn(expectedResult);
        
        // Act
        Map<String, List<String>> result = service.processStreams(sections, 3);
        
        // Assert
        assertSame(expectedResult, result);
        verify(mockStrategy).deduplicate(sections, 3);
    }
    
    @Test
    void testProcessStreamsWithDefaultTopPositions() {
        // Arrange
        List<Section> sections = Arrays.asList(new Section());
        Map<String, List<String>> expectedResult = new HashMap<>();
        when(mockStrategy.deduplicate(sections, 3)).thenReturn(expectedResult);
        
        // Act
        Map<String, List<String>> result = service.processStreams(sections);
        
        // Assert
        assertSame(expectedResult, result);
        verify(mockStrategy).deduplicate(sections, 3);
    }
    
    @Test
    void testProcessStreamsWithNullSections() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> service.processStreams(null, 3));
    }
    
    @Test
    void testProcessStreamsWithInvalidTopPositions() {
        // Arrange
        List<Section> sections = Arrays.asList(new Section());
        
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> service.processStreams(sections, 0));
        assertThrows(IllegalArgumentException.class, () -> service.processStreams(sections, -1));
    }
    
    @Test
    void testProcessStreamsWithStrategyException() {
        // Arrange
        List<Section> sections = Arrays.asList(new Section());
        when(mockStrategy.deduplicate(sections, 3)).thenThrow(new RuntimeException("Strategy error"));
        
        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> service.processStreams(sections, 3));
        assertTrue(exception.getMessage().contains("Failed to process streams"));
    }
}