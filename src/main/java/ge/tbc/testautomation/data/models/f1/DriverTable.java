package ge.tbc.testautomation.data.models.f1;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class DriverTable {
    @JsonProperty("season")
    private String season;

    @JsonProperty("Drivers")
    private List<Driver> drivers;

    public String getSeason() {
        return season;
    }

    public List<Driver> getDrivers() {
        return drivers;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public void setDrivers(List<Driver> drivers) {
        this.drivers = drivers;
    }
}
