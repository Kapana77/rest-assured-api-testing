package ge.tbc.testautomation.data.models.f1;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MRData {
    @JsonProperty("series")
    private String series;

    @JsonProperty("total")
    private String total;

    @JsonProperty("DriverTable")
    private DriverTable driverTable;

    public String getSeries() {
        return series;
    }

    public String getTotal() {
        return total;
    }

    public DriverTable getDriverTable() {
        return driverTable;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public void setDriverTable(DriverTable driverTable) {
        this.driverTable = driverTable;
    }
}
