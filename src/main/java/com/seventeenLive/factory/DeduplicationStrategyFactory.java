package com.seventeenlive.factory;

import com.seventeenlive.strategy.DeduplicationStrategy;
import com.seventeenlive.strategy.GreedyDeduplicationStrategy;

/**
 * Factory for creating deduplication strategies.
 * Follows Factory Pattern and Open/Closed Principle.
 */
public class DeduplicationStrategyFactory {
    
    public enum StrategyType {
        GREEDY
    }
    
    /**
     * Creates a deduplication strategy based on the specified type
     */
    public static DeduplicationStrategy createStrategy(StrategyType type) {
        if (type == null) {
            throw new IllegalArgumentException("Strategy type cannot be null");
        }
        
        switch (type) {
            case GREEDY:
                return new GreedyDeduplicationStrategy();
            default:
                throw new IllegalArgumentException("Unsupported strategy type: " + type);
        }
    }
    
    /**
     * Created the default strategy (Greedy)
     */
    public static DeduplicationStrategy createDefaultStrategy() {
        return createStrategy(StrategyType.GREEDY);
    }
}