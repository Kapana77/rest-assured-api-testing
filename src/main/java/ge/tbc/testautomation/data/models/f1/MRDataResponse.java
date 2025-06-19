package ge.tbc.testautomation.data.models.f1;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MRDataResponse {
    @JsonProperty("MRData")
    private MRData mrData;

    public MRData getMrData() {
        return mrData;
    }

    public void setMrData(MRData mrData) {
        this.mrData = mrData;
    }
}
