package com.seventeenlive.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a single streamer data entry.
 * Follows Single Responsibility Principle - only handles streamer data.
 */
public class StreamerData {
    
    @JsonProperty("streamerID")
    private String streamerId;
    
    public StreamerData() {
        // Default constructor for Jackson
    }
    
    public StreamerData(String streamerId) {
        this.streamerId = streamerId;
    }
    
    public String getStreamerId() {
        return streamerId;
    }
    
    public void setStreamerId(String streamerId) {
        this.streamerId = streamerId;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        StreamerData that = (StreamerData) obj;
        return streamerId != null ? streamerId.equals(that.streamerId) : that.streamerId == null;
    }
    
    @Override
    public int hashCode() {
        return streamerId != null ? streamerId.hashCode() : 0;
    }
    
    @Override
    public String toString() {
        return "StreamerData{streamerId='" + streamerId + "'}";
    }
}