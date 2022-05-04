package com.web.coronavirustracker.models;

public class locationStats {
    
    private String state;
    private String country;
    private String latestTotalCases;
    private int delta;

    public int getDelta() {
        return delta;
    }
    public void setDelta(int delta) {
        this.delta = delta;
    }

    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public String getLatestTotalCases() {
        return latestTotalCases;
    }
    public void setLatestTotalCases(String latestTotalCases) {
        this.latestTotalCases = latestTotalCases;
    }

    @Override
    public String toString() {
        return "locationStats [country=" + country + ", latestTotalCases=" + latestTotalCases + ", state=" + state
                + "]";
    }

    
}
