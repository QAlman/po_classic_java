package common.dto;

import com.google.gson.JsonElement;

public class TestData {
    public JsonElement testData;

    public TestData(JsonElement testData) {
        this.testData = testData;
    }

    public String getVariable(String name) {
        return testData.getAsJsonObject().get(name).getAsString();
    }

    @Override
    public String toString() {
        return testData.toString();
    }
}
