package com.seventeenlive.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Section {
    
    @JsonProperty("sectionData")
    private List<StreamerData> sectionData;
    
    @JsonProperty("lokalisedKey")
    private String lokalisedKey;
    
    @JsonProperty("sectionID")
    private String sectionId;
    
    @JsonProperty("mlDynamicLabel")
    private Boolean mlDynamicLabel;
    
    @JsonProperty("labelID")
    private String labelId;
    
    public Section() {
        this.sectionData = new ArrayList<>();
    }
    
    public Section(String sectionId, List<StreamerData> sectionData) {
        this.sectionId = sectionId;
        this.sectionData = sectionData != null ? new ArrayList<>(sectionData) : new ArrayList<>();
    }

    public List<StreamerData> getSectionData() {
        return sectionData;
    }
    
    public void setSectionData(List<StreamerData> sectionData) {
        this.sectionData = sectionData != null ? new ArrayList<>(sectionData) : new ArrayList<>();
    }
    
    public String getLokalisedKey() {
        return lokalisedKey;
    }
    
    public void setLokalisedKey(String lokalisedKey) {
        this.lokalisedKey = lokalisedKey;
    }
    
    public String getSectionId() {
        return sectionId;
    }
    
    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }
    
    public Boolean getMlDynamicLabel() {
        return mlDynamicLabel;
    }
    
    public void setMlDynamicLabel(Boolean mlDynamicLabel) {
        this.mlDynamicLabel = mlDynamicLabel;
    }
    
    public String getLabelId() {
        return labelId;
    }
    
    public void setLabelId(String labelId) {
        this.labelId = labelId;
    }
    
    /**
     * Gets the top N streamers from this section
     */
    public List<String> getTopStreamers(int count) {
        List<String> topStreamers = new ArrayList<>();
        int limit = Math.min(count, sectionData.size());
        
        for (int i = 0; i < limit; i++) {
            topStreamers.add(sectionData.get(i).getStreamerId());
        }
        
        return topStreamers;
    }
    
    /**
     * Gets all streamer IDs as a list
     */
    public List<String> getAllStreamerIds() {
        List<String> streamerIds = new ArrayList<>();
        for (StreamerData data : sectionData) {
            streamerIds.add(data.getStreamerId());
        }
        return streamerIds;
    }
    
    @Override
    public String toString() {
        return "Section{" +
                "sectionId='" + sectionId + '\'' +
                ", streamersCount=" + (sectionData != null ? sectionData.size() : 0) +
                '}';
    }
}